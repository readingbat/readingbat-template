# Release Notes

## Unreleased

Build-housekeeping follow-ups to 1.7.0. No DSL or runtime behavior changes.

### Highlights

- **Fixed broken fat-jar make target.** `make uberjar` (and the previously documented `./gradlew uberjar`) referenced a task that does not exist. The Makefile now invokes `./gradlew buildFatJar`; `CLAUDE.md` and `llms.txt` updated to match.
- **Single fat-jar configuration path.** Switched to Ktor's idiomatic `ktor { fatJar { archiveFileName = "server.jar" } }` block; `tasks.shadowJar` now only carries the META-INF signature/license excludes. Reverses the 1.7.0 direction (consolidation onto `tasks.shadowJar`) in favor of the Ktor-recommended API.
- **JVM and Gradle versions now in the catalog.** `gradle = "9.5.0"` and `jvm = "17"` are tracked under `[versions]` in `gradle/libs.versions.toml`. `build.gradle.kts` reads `libs.versions.jvm.get().toInt()` for the toolchain; `Makefile` reads `gradle` from the catalog when running `upgrade-wrapper`.
- **Catalog plugin alias renamed.** `versions` → `ben-manes-versions` to avoid the name collision with the `[versions]` table. Call site is `alias(libs.plugins.ben.manes.versions)`.

### Upgrade Notes

- Forks invoking `./gradlew uberjar` should switch to `./gradlew buildFatJar` (or use `make uberjar`).
- Forks referencing `libs.plugins.versions` should rename to `libs.plugins.ben.manes.versions`.

### Verification

- `./gradlew buildFatJar` — produces `build/libs/server.jar` (~184 MB).
- `./gradlew tasks` and `./gradlew help` resolve cleanly.
- `make -n upgrade-wrapper` expands to `./gradlew wrapper --gradle-version=9.5.0 --distribution-type=bin`.

---

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
- `./gradlew buildFatJar` — produces a runnable `build/libs/server.jar`.
- `./gradlew properties` — confirms `group=com.readingbat` and `version=1.7.0`.

---

**Full Changelog:** https://github.com/readingbat/readingbat-template/compare/1.6.0...1.7.0
