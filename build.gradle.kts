import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
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

dependencies {
  implementation(libs.readingbat.core)
  implementation(libs.core.utils)
  implementation(libs.kotlin.logging)

  testImplementation(libs.bundles.testing)
}

kotlin {
  jvmToolchain(17)
}

tasks.register("stage") {
  dependsOn("clean", "build")
}

tasks.named("build") {
  mustRunAfter("clean")
}

tasks.shadowJar {
  archiveFileName.set("server.jar")
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
    showStandardStreams = false
  }
}
