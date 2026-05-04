# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ReadingBat Template — a Kotlin/Ktor-based template for teachers to author ReadingBat.com content. ReadingBat is a code-reading practice site (inspired by CodingBat) where students read and trace code rather than write it. Content is defined via a Kotlin DSL supporting Python, Java, and Kotlin challenges.

## Build & Run Commands

- **Build (no tests):** `./gradlew build -xtest` or `make build`
- **Run server:** `./gradlew run` or `make run`
- **Run all tests:** `./gradlew --rerun-tasks check` or `make tests`
- **Run a single test class:** `./gradlew test --tests "ContentTests"`
- **Run a single test:** `./gradlew test --tests "ContentTests.Test all challenges"`
- **Build fat JAR:** `./gradlew uberjar` (outputs `build/libs/server.jar`)
- **Check dependency updates:** `./gradlew dependencyUpdates` (default Make target)
- **Continuous compile:** `make cc` (rebuilds on file changes, skips tests)

## Architecture

### Content Definition (DSL)

`src/main/kotlin/Content.kt` — the central file defining all challenge content using the `readingBatContent` DSL. The DSL hierarchy is:
- `readingBatContent` → `python {}` / `java {}` / `kotlin {}` (LanguageGroup)
  - → `group("Name") {}` (ChallengeGroup)
    - → `challenge("Name") {}` (individual Challenge)
    - → `includeFiles` / `includeFilesWithType` (glob-based bulk import)

The `repo` property switches between `GitHubRepo` (production) and `FileSystemSource` (local dev) based on `isProduction()`.

### Challenge Source Files

Challenge code lives outside the Kotlin source tree in language-specific directories:
- **Python:** `python/<packageName>/` (e.g., `python/group1/find_it.py`)
- **Java:** `src/main/java/<packageName>/` (e.g., `src/main/java/group1/JoinEnds.java`)
- **Kotlin:** `src/main/kotlin/<packageName>/` (e.g., `src/main/kotlin/kgroup1/StringLambda1.kt`)

### Server

`src/main/kotlin/ContentServer.kt` — minimal entry point that delegates to `ReadingBatServer.start()` from the `readingbat-core` library. Server configuration is in `src/main/resources/application.conf` (HOCON format) — controls port (default 8080), production mode, DBMS, content caching, script pool sizes, and Ktor watch mode for hot reload during development.

### Testing

`src/test/kotlin/ContentTests.kt` — uses Kotest (StringSpec style with `init {}` block) and Ktor's `testApplication`. Tests use the `readingbat-kotest` library's `TestSupport` helpers to verify challenges accept correct answers and reject incorrect ones. The test framework iterates over all languages/groups/challenges automatically.

**Important:** Tests must call `initTestProperties()` before accessing `content` (e.g., via `beforeEach`). This sets `IS_PRODUCTION=false` and `IS_TESTING=true` in the `KtorProperty` config store — without it, `isProduction()` in `Content.kt` will throw `Property IS_PRODUCTION not initialized`.

## Key Dependencies

Managed in `gradle/libs.versions.toml`:
- `readingbat-core` / `readingbat-kotest` — core DSL, server, and test framework (Maven Central)
- Ktor — web server framework
- Kotest — test framework (JUnit5 runner)
- JVM toolchain: Java 17

Test dependencies are exposed as a `[bundles] testing` entry and consumed in `build.gradle.kts` via `libs.bundles.testing`.

`group` and `version` live in `gradle.properties` (not `build.gradle.kts`). Repository configuration lives in `settings.gradle.kts` with `FAIL_ON_PROJECT_REPOS` enforcement — do not add per-project repositories to `build.gradle.kts`.

## DSL Conventions

- Java challenge return types are inferred from source code; Python and Kotlin challenges require explicit `returnType` (e.g., `BooleanType`, `StringType`, `IntType`)
- `codingBatEquiv` on a challenge adds a cross-reference link to the equivalent CodingBat problem
- `packageName` in a group maps directly to the filesystem directory containing challenge source files
