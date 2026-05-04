# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

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

[Unreleased]: https://github.com/readingbat/readingbat-template/compare/1.7.0...HEAD
[1.7.0]: https://github.com/readingbat/readingbat-template/compare/1.6.0...1.7.0
[1.6.0]: https://github.com/readingbat/readingbat-template/releases/tag/1.6.0
