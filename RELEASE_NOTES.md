# Release Notes

## v1.7.0 — 2026-05-03

A build-housekeeping release. No DSL or runtime behavior changes — existing `Content.kt` definitions compile and run unchanged.

### Highlights

- **Project metadata moved to `gradle.properties`:** `group` and `version` are no longer hard-coded in `build.gradle.kts`.
- **Single source of truth for fat-jar config:** consolidated into `tasks.shadowJar`; removed the duplicate `ktor.fatJar` block.
- **Cleaner version catalog:** all entries in `libs.versions.toml` now use the `module = "group:name"` style, and test dependencies are grouped into a `[bundles] testing` entry consumed via `libs.bundles.testing`.
- **Dropped the unused `idea` Gradle plugin.**

### Dependencies

| Library          | Version  |
|------------------|----------|
| Kotlin           | 2.3.21   |
| Ktor             | 3.4.3    |
| Kotest           | 6.1.11   |
| readingbat-core  | 3.1.5    |
| core-utils       | 2.8.2    |
| kotlin-logging   | 8.0.02   |

### Documentation

- Added `CHANGELOG.md` (Keep a Changelog format).
- Updated `README.md` and `CLAUDE.md` to reflect the new gradle layout.

### Upgrade Notes

- If you have a fork that overrides `version` in `build.gradle.kts`, move it to `gradle.properties` instead.
- If you previously referenced JitPack in a fork, remove any `maven { url "https://jitpack.io" }` declarations — they are rejected by `FAIL_ON_PROJECT_REPOS` in `settings.gradle.kts`.
- JDK 17 toolchain is required.

### Verification

- `./gradlew --rerun-tasks check` — all challenge tests pass.
- `./gradlew uberjar` — produces a runnable `build/libs/server.jar`.
- `./gradlew properties` — confirms `group=com.readingbat` and `version=1.7.0`.

---

**Full Changelog:** https://github.com/readingbat/readingbat-template/compare/1.6.0...1.7.0
