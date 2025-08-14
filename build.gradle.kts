plugins {
  idea
  java
  application
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.versions)
  alias(libs.plugins.ktor.plugin)
}

repositories {
  google()
  mavenCentral()
  maven { url = uri("https://jitpack.io") }
}

val mainName = "ContentServer"
val appName = "server"

// This is for ./gradlew run
application {
  mainClass.set(mainName)
}

description = "ReadingBat Site"
group = "com.github.readingbat"
version = "1.6.0"

dependencies {
  implementation(libs.readingbat.core)
  implementation(libs.readingbat.kotest)
  implementation(libs.core.utils)
  implementation(libs.kotlin.logging)

  // Test dependencies
  testImplementation(libs.ktor.server.test)
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.kotest.runner)

  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(17)

  configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
  }

}

idea {
  module {
    isDownloadSources = true
    isDownloadJavadoc = true
  }
}

tasks.register("stage") {
  dependsOn("build", "clean")
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
    events("passed", "skipped", "failed", "standardOut", "standardError")
    exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    showStandardStreams = true
  }
}