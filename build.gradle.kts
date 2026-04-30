import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  idea
  application
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.versions)
  alias(libs.plugins.ktor.plugin)
}

// This is for ./gradlew run
application {
  mainClass = "ContentServerKt"
}

description = "ReadingBat Site"
group = "com.readingbat"
version = "1.6.0"

dependencies {
  implementation(libs.readingbat.core)
  implementation(libs.core.utils)
  implementation(libs.kotlin.logging)

  // Test dependencies
  testImplementation(libs.readingbat.kotest)
  testImplementation(libs.ktor.server.config.yaml)
  testImplementation(libs.ktor.server.test)
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.kotest.runner)
}

kotlin {
  jvmToolchain(17)
}

idea {
  module {
    isDownloadSources = true
    isDownloadJavadoc = true
  }
}

tasks.register("stage") {
  dependsOn("clean", "build")
}

tasks.named("build") {
  mustRunAfter("clean")
}

ktor {
  fatJar {
    archiveFileName.set("server.jar")
  }
}

tasks.shadowJar {
  isZip64 = true
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  exclude("META-INF/*.SF")
  exclude("META-INF/*.DSA")
  exclude("META-INF/*.RSA")
  exclude("LICENSE*")
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    showStandardStreams = true
  }
}
