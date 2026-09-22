# CLAUDE.md

## Project Overview

ReadingBat Template — a Kotlin/Ktor-based template for teachers to author ReadingBat.com content. ReadingBat is a code-reading practice site (inspired by CodingBat) where students read and trace code rather than write it. Content is defined via a Kotlin DSL supporting Python, Java, and Kotlin challenges.

## Build & Run Commands

- **Discover Makefile targets:** `make help` (renders self-documented `## …` descriptions)

## Testing

**Important:** Tests must call `initTestProperties()` before accessing `content` (e.g., via `beforeEach`). This sets `IS_PRODUCTION=false` and `IS_TESTING=true` in the `KtorProperty` config store — without it, `isProduction()` in `Content.kt` will throw `Property IS_PRODUCTION not initialized`.

As of readingbat-core 3.2.0, `correctAnswers` is a suspend function — call it as `correctAnswers()` and wrap answer assertions in `runBlocking { … }` (see `ContentTests.kt`).

## Build Configuration

`group` and `version` live in `gradle.properties` (not `build.gradle.kts`). Repository configuration lives in `settings.gradle.kts` with `FAIL_ON_PROJECT_REPOS` enforcement — do not add per-project repositories to `build.gradle.kts`.

Gradle's configuration cache is enabled (`org.gradle.configuration-cache=true` in `gradle.properties`) — keep new build logic configuration-cache-compatible.

`.gitattributes` pins `gradlew` to `lf` and marks `*.jar` / `*.png` `binary`, so the wrapper jar is never EOL-mangled in a checkout.

The Gradle version is driven by the `gradle-wrapper` key in `gradle/libs.versions.toml`, not by `gradle-wrapper.properties` directly. To upgrade: bump that key, then run `make upgrade-wrapper` — it runs `./gradlew wrapper` twice, which is Gradle's documented procedure (the first pass rewrites the properties with the old jar, the second regenerates the jar itself). Don't hand-edit `distributionUrl` or the jar.

## Continuous Integration

Run `make tests` and `make lint` locally before pushing — a formatting violation fails CI just as a test failure does.

## DSL Conventions

- Java challenge return types are inferred from source code; Python and Kotlin challenges require explicit `returnType` (e.g., `BooleanType`, `StringType`, `IntType`)
- `codingBatEquiv` on a challenge adds a cross-reference link to the equivalent CodingBat problem
- `packageName` in a group maps directly to the filesystem directory containing challenge source files
