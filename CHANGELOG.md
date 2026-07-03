# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.8.0] - 2026-06-15

### Added

- Detekt (`dev.detekt` 2.0.0-alpha.4) and Kotlinter (`org.jmailen.kotlinter` 5.5.0) Gradle plugins for static analysis and ktlint formatting.
- `.editorconfig` with project-wide formatting rules (UTF-8, LF, 2-space indent, 120-char max) and a curated set of disabled ktlint rules tuned to this codebase.
- Makefile targets `lint`, `format`, `detekt`, `detekt-baseline`, and a self-documenting `help` target driven by `##` annotations on each target.
- `_require-gradle-version` guard target that fails fast if `gradle/libs.versions.toml` parsing yields an empty version.

### Changed

- **Upgraded the JVM toolchain 17 → 25.** `gradle/libs.versions.toml` sets `jvm = "25"` (read by `build.gradle.kts`) and `system.properties` sets `java.runtime.version=25` for Heroku. A JDK 25 is now required to build.
- Bumped dependencies: `readingbat-core` 3.1.5 → 3.2.0, Kotlin 2.3.21 → 2.4.0, Ktor 3.4.3 → 3.5.0, Kotest 6.1.11 → 6.2.0, `core-utils` 2.8.2 → 2.9.2, `kotlin-logging` 8.0.02 → 8.0.4, and the Gradle wrapper 9.5.0 → 9.5.1.
- Updated `ContentTests` for readingbat-core 3.2.0: `correctAnswers` is now the suspend function `correctAnswers()`, so the answer assertion runs inside a `runBlocking { … }` block.
- Renamed the Kotlin challenge package `kgroup1` → `kgroup` for consistency with the documented layout.
- Enabled Gradle configuration cache (`org.gradle.configuration-cache=true`).
- Switched `tasks.shadowJar` to `DuplicatesStrategy.WARN` and dropped the `LICENSE*` exclude so duplicate-resource collisions are visible during the fat-jar build.
- Hardened `Makefile` `GRADLE_VERSION` parsing with `grep -E` + `sed -E` instead of a single positional `sed -n` regex; `upgrade-wrapper` now runs the Gradle wrapper twice so the wrapper script itself is regenerated.
- Narrowed the `io.ktor.server.testing.*` wildcard import in `ContentTests` to the specific `testApplication` symbol, per ktlint.
- `make uberjar` (and the documented `./gradlew uberjar` command) was broken — no such task exists. Pointed `make uberjar` at `./gradlew buildFatJar` and updated `CLAUDE.md` / `llms.txt` accordingly.
- Switched fat-jar configuration to Ktor's idiomatic `ktor { fatJar { archiveFileName = "server.jar" } }` block. `tasks.shadowJar` now only carries signature excludes (`META-INF/*.SF`, `*.DSA`, `*.RSA`). This reverses the 1.7.0 consolidation toward `tasks.shadowJar` and removes parallel configuration paths.
- Promoted `jvm` and `gradle-wrapper` versions into `gradle/libs.versions.toml`. `build.gradle.kts` reads the JVM toolchain via `libs.versions.jvm.get().toInt()`; the `Makefile`'s `upgrade-wrapper` target reads the Gradle version from the catalog.
- Renamed the version-catalog plugin alias `versions` → `ben-manes-versions` (the prior name collided with the `[versions]` table). Call site is now `alias(libs.plugins.ben.manes.versions)`.
- Extracted repeated `"clean"` / `"build"` task-name strings to `cleanTask` / `buildTask` references in `build.gradle.kts`.

### Fixed

- Source files (`Content.kt`, `kgroup/IntLambda1.kt`, `kgroup/StringLambda1.kt`) now end with a trailing newline, per `.editorconfig`.

## [1.7.0] - 2026-05-03

### Changed

- Moved `group` and `version` from `build.gradle.kts` to `gradle.properties`.
- Consolidated fat-jar configuration into a single `tasks.shadowJar` block; removed the redundant `ktor.fatJar` block.
- Standardized dependency coordinate style in `libs.versions.toml` to use `module = "group:name"` consistently.
- Grouped test dependencies into a `[bundles] testing` entry; `build.gradle.kts` now references `libs.bundles.testing`.
- Bumped `kotlin-logging` to 8.0.02, `readingbat` to 3.1.5, and `core-utils` to 2.8.2.
- Updated `README.md` and `CLAUDE.md` to document the new gradle layout (properties, settings, and the testing bundle).

### Added

- `CHANGELOG.md` and `RELEASE_NOTES.md`.

### Removed

- Dropped the unused `idea` Gradle plugin and its module configuration.

## [1.6.0] - 2026-04-30

### Changed

- Migrated repository configuration to `settings.gradle.kts` with `FAIL_ON_PROJECT_REPOS` enforcement.
- Bumped Gradle to 9.5.0; updated Kotlin, Ktor, and dependency versions.
- Migrated packages from JitPack to Maven Central.
- Adjusted `readingbat` group ID and cleaned up build configuration.

## [1.5.x]

### Changed

- Updated `readingbat` to 3.0.5; added `shadowJar` configuration and test logging.
- Modernized README and simplified build configuration.
- Fixed tests by adding `initTestProperties` and `ktor-server-config-yaml`.
- Upgraded Java toolchain to 17; removed `redisEnabled` config.
- Upgraded Gradle to 9.4.0; added `CLAUDE.md` and `llms.txt`.

## [1.4.x]

### Changed

- Upgraded Gradle to 9.0.0 and updated dependencies in `libs.versions.toml`.
- Converted to using `libs.versions.toml` for dependency management.

## Earlier

- Upgrades to Kotlin 2.1.0 / Ktor 3.0.1, `readingbat-core` 2.0.0, and various jar refreshes.

[1.8.0]: https://github.com/readingbat/readingbat-template/compare/1.7.0...1.8.0
[1.7.0]: https://github.com/readingbat/readingbat-template/compare/1.6.0...1.7.0
[1.6.0]: https://github.com/readingbat/readingbat-template/releases/tag/1.6.0
