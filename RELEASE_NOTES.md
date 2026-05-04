# Release Notes

## v1.6.0

### Highlights

- **Build cleanup:** Repository configuration moved to `settings.gradle.kts` with `FAIL_ON_PROJECT_REPOS` to prevent project-level repo drift.
- **JitPack → Maven Central:** All `readingbat` and supporting artifacts now resolve from Maven Central.
- **Gradle 9.5.0** with refreshed Kotlin and Ktor toolchains.

### Build & Tooling

- Gradle wrapper bumped to **9.5.0**.
- Kotlin and Ktor pinned via `libs.versions.toml`.
- `readingbat` group ID adjusted to match the new Maven Central coordinates.
- Fat-jar build (`./gradlew uberjar`) continues to produce `build/libs/server.jar`.

### Dependencies

| Library          | Version  |
|------------------|----------|
| Kotlin           | 2.3.21   |
| Ktor             | 3.4.3    |
| Kotest           | 6.1.11   |
| readingbat-core  | 3.1.5    |
| core-utils       | 2.8.2    |
| kotlin-logging   | 8.0.02   |

### Upgrade Notes

- No DSL changes — existing `Content.kt` definitions continue to compile unchanged.
- If you previously referenced JitPack in a fork, remove any `maven { url "https://jitpack.io" }` declarations; they are now rejected by `FAIL_ON_PROJECT_REPOS`.
- JDK 17 toolchain is required.

### Verification

- `./gradlew --rerun-tasks check` — all challenge tests pass.
- `./gradlew uberjar` — produces a runnable `server.jar`.

---

**Full Changelog:** https://github.com/readingbat/readingbat-template/compare/1.5.0...1.6.0
