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

val cleanTask = tasks.named("clean")
val buildTask = tasks.named("build")

tasks.register("stage") {
  dependsOn(cleanTask, buildTask)
}

buildTask {
  mustRunAfter(cleanTask)
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
    events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    exceptionFormat = TestExceptionFormat.FULL
    showStandardStreams = false
  }
}
