import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ben.manes.versions)
  alias(libs.plugins.detekt)
  alias(libs.plugins.kotlinter)
  alias(libs.plugins.ktor.plugin)
}

description = "ReadingBat Site"

application {
  mainClass = "ContentServerKt"
}

dependencies {
  implementation(libs.readingbat.core)
  implementation(libs.core.utils)
  implementation(libs.kotlin.logging)

  testImplementation(libs.bundles.testing)
}

tasks.register("stage") {
  group = "distribution"
  description = "Clean-builds the project for deployment."
  dependsOn("clean", "build")
}

tasks.named("build") {
  mustRunAfter("clean")
}

configureKotlin()
configureDetekt()
configureKotlinter()
configureKtor()
configureShadowJar()
configureTest()
configureVersions()

fun Project.configureKotlin() {
  kotlin {
    jvmToolchain(libs.versions.jvm.get().toInt())
  }

  // Run the unused-return-value checker over production code only. Kotest's
  // assertion DSL (e.g. shouldBe) returns its receiver, and tests intentionally
  // discard that result, so applying the checker to the test source set would
  // emit only false-positive warnings.
  tasks.named<KotlinCompile>("compileKotlin") {
    compilerOptions {
      freeCompilerArgs.add("-Xreturn-value-checker=check")
    }
  }
}

fun Project.configureDetekt() {
  detekt {
    source.setFrom("src/main/kotlin", "src/test/kotlin")
    buildUponDefaultConfig = true
    parallel = true
  }
}

fun Project.configureKotlinter() {
  kotlinter {
    ignoreFormatFailures = false
    ignoreLintFailures = false
    reporters = arrayOf("checkstyle", "plain")
  }
}

fun Project.configureKtor() {
  ktor {
    fatJar {
      archiveFileName = "server.jar"
    }
  }
}

fun Project.configureShadowJar() {
  // Ktor's `buildFatJar` task delegates to shadow; this block configures that output.
  tasks.shadowJar {
    isZip64 = true
    duplicatesStrategy = DuplicatesStrategy.WARN
    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
  }
}

fun Project.configureTest() {
  tasks.test {
    useJUnitPlatform()

    testLogging {
      events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_ERROR)
      exceptionFormat = TestExceptionFormat.FULL
      showStandardStreams = false
    }
  }
}

fun Project.configureVersions() {
  // A pre-release qualifier is a `.` or `-` delimiter followed by a known unstable
  // keyword. `m\d` matches milestones (`-M1`/`.M2`) without catching stable classifiers
  // like `-macos`/`-MR1`, and the `[.-]` delimiter catches both dash-style (`-alpha`)
  // and dot-style (Netty's `.Beta1`) qualifiers while leaving `-jre`/`.Final` stable.
  val preReleaseQualifier =
    Regex("""[.-](rc|beta|alpha|m\d|cr|snapshot|eap|dev|milestone|pre)""", RegexOption.IGNORE_CASE)

  fun isNonStable(version: String): Boolean = preReleaseQualifier.containsMatchIn(version)

  tasks.withType<DependencyUpdatesTask>().configureEach {
    notCompatibleWithConfigurationCache("the dependency updates plugin is not compatible with the configuration cache")
    // Reject a pre-release candidate only when the current version is stable. For
    // dependencies we intentionally track on a pre-release line (e.g. a detekt
    // alpha), newer pre-releases are still surfaced as available updates.
    rejectVersionIf {
      isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
  }
}
