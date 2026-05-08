import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  application
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ben.manes.versions)
  alias(libs.plugins.ktor.plugin)
}

// This is for ./gradlew run
application {
  mainClass = "ContentServerKt"
}

description = "ReadingBat Site"

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

tasks.shadowJar {
  isZip64 = true
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "LICENSE*")
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    exceptionFormat = TestExceptionFormat.FULL
    showStandardStreams = false
  }
}
