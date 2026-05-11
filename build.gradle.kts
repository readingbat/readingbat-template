import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  application
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ben.manes.versions)
  alias(libs.plugins.detekt)
  alias(libs.plugins.kotlinter)
  alias(libs.plugins.ktor.plugin)
}

description = "ReadingBat Site"

detekt {
  buildUponDefaultConfig = true
}

application {
  mainClass = "ContentServerKt"
}

dependencies {
  implementation(libs.readingbat.core)
  implementation(libs.core.utils)
  implementation(libs.kotlin.logging)

  testImplementation(libs.bundles.testing)
}

kotlin {
  jvmToolchain(libs.versions.jvm.get().toInt())
}

detekt {
  source.setFrom("src/main/kotlin", "src/test/kotlin")
  buildUponDefaultConfig = true
  parallel = true
}

kotlinter {
  ignoreFormatFailures = false
  ignoreLintFailures = false
  reporters = arrayOf("checkstyle", "plain")
}

tasks.register("stage") {
  dependsOn("clean", "build")
}

(tasks.named("build")) {
  mustRunAfter("clean")
}

ktor {
  fatJar {
    archiveFileName = "server.jar"
  }
}

// Ktor's `buildFatJar` task delegates to shadow; this block configures that output.
tasks.shadowJar {
  isZip64 = true
  duplicatesStrategy = DuplicatesStrategy.WARN
  exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_ERROR)
    exceptionFormat = TestExceptionFormat.FULL
    showStandardStreams = false
  }
}
