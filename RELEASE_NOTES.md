# Release Notes

## v1.8.0 — 2026-07-03

A linting and toolchain-modernization release on top of 1.7.0. The content DSL is unchanged, but the project now targets **JDK 25** and picks up **readingbat-core 3.2.1**, which adjusts one test call site.

### Highlights

- **JVM toolchain 17 → 25.** `gradle/libs.versions.toml` sets `jvm = "25"` (read by `build.gradle.kts` for the toolchain). **A JDK 25 is now required to build.**
- **readingbat-core 3.1.5 → 3.2.1.** `correctAnswers` is now a suspend function; `ContentTests` calls `correctAnswers()` inside a `runBlocking { … }` block. Existing `Content.kt` definitions compile and run unchanged.
- **Linting wired in.** Detekt (`dev.detekt` 2.0.0-alpha.5) and Kotlinter (`org.jmailen.kotlinter` 5.5.0) Gradle plugins are now applied, with matching `make lint` / `make format` / `make detekt` / `make detekt-baseline` shortcuts.
- **`.editorconfig` landed.** Project-wide formatting rules (UTF-8, LF, 2-space indent, 120-char max, final newline) plus a curated set of disabled ktlint rules tuned to this codebase.
- **Gradle configuration cache enabled.** `org.gradle.configuration-cache=true` in `gradle.properties`. New build logic should be configuration-cache-compatible.
- **Self-documenting Makefile.** `##` annotations on each target are surfaced via `make help`; a `_require-gradle-version` guard prevents silent wrapper-upgrade failures, and `upgrade-wrapper` runs the wrapper twice so the wrapper script regenerates.
- **ShadowJar duplicates surface.** `tasks.shadowJar` now uses `DuplicatesStrategy.WARN` (dropping the `LICENSE*` exclude) so duplicate-resource collisions in the fat jar are visible.
- **Fixed broken fat-jar make target.** `make uberjar` (and the previously documented `./gradlew uberjar`) referenced a task that does not exist. The Makefile now invokes `./gradlew buildFatJar`; `CLAUDE.md` and `llms.txt` updated to match.
- **Single fat-jar configuration path.** Switched to Ktor's idiomatic `ktor { fatJar { archiveFileName = "server.jar" } }` block; `tasks.shadowJar` now only carries the META-INF signature excludes. Reverses the 1.7.0 direction (consolidation onto `tasks.shadowJar`) in favor of the Ktor-recommended API.
- **JVM and Gradle versions now in the catalog.** `gradle-wrapper = "9.6.1"` and `jvm = "25"` are tracked under `[versions]` in `gradle/libs.versions.toml`. `build.gradle.kts` reads `libs.versions.jvm.get().toInt()` for the toolchain; `Makefile` reads `gradle-wrapper` from the catalog when running `upgrade-wrapper`.
- **Catalog plugin alias renamed.** `versions` → `ben-manes-versions` to avoid the name collision with the `[versions]` table. Call site is `alias(libs.plugins.ben.manes.versions)`.
- **Kotlin challenge package renamed.** `kgroup1` → `kgroup` to match the documented layout.
- **Dropped Heroku deployment support.** Removed `Procfile`, `system.properties`, and the `heroku` / `logs` Makefile targets; deploy the `./gradlew buildFatJar` output (`build/libs/server.jar`) instead.

### Dependencies

| Library          | Version       |
|------------------|---------------|
| Kotlin           | 2.4.0         |
| Ktor             | 3.5.1         |
| Kotest           | 6.2.1         |
| readingbat-core  | 3.2.1         |
| core-utils       | 2.9.3         |
| kotlin-logging   | 8.0.4         |
| detekt           | 2.0.0-alpha.5 |
| kotlinter        | 5.5.0         |
| Gradle           | 9.6.1         |
| JDK toolchain    | 25            |

### Upgrade Notes

- **Install a JDK 25** — the toolchain target moved from 17 to 25.
- Forks invoking `./gradlew uberjar` should switch to `./gradlew buildFatJar` (or use `make uberjar`).
- Forks referencing `libs.plugins.versions` should rename to `libs.plugins.ben.manes.versions`.
- Forks referencing the `kgroup1` package should rename it to `kgroup`.
- Run `make lint` (or `./gradlew lintKotlin detekt`) before submitting changes — CI/local builds will surface style and static-analysis violations.

### Verification

- `./gradlew --rerun-tasks check` — all challenge tests pass.
- `./gradlew buildFatJar` — produces `build/libs/server.jar`.
- `./gradlew lintKotlin detekt` — both checks run cleanly on `master`.
- `make -n upgrade-wrapper` expands to `./gradlew wrapper --gradle-version=9.6.1 --distribution-type=bin` (run twice).
- `make help` lists every documented Makefile target.

**Full Changelog:** https://github.com/readingbat/readingbat-template/compare/1.7.0...1.8.0

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
