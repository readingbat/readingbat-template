[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

# ReadingBat Template

A Kotlin/Ktor template for teachers to author [ReadingBat.com](https://www.readingbat.com) content —
a code-reading practice site inspired by [CodingBat.com](https://codingbat.com).

ReadingBat helps students learn to **read and trace** code before they start writing it.
Challenges are defined using a Kotlin DSL and support Python, Java, and Kotlin.

This template lets you create your own ReadingBat content. Once it's ready,
send us a note and we'll link [ReadingBat.com](https://www.readingbat.com) to it.

## Prerequisites

- **Java 17+** (the project uses a JVM 17 toolchain)
- **Git** (to clone the repo and manage content)

> Gradle is included via the [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) — no separate install needed.

## Getting Started

```bash
git clone https://github.com/readingbat/readingbat-template.git
cd readingbat-template
./gradlew build -xtest   # compile without running tests
./gradlew run            # start the server on http://localhost:8080
```

See the wiki for more details:
[Quickstart](https://github.com/readingbat/readingbat-template/wiki/Quickstart)
| [Setup](https://github.com/readingbat/readingbat-template/wiki/Setup)
| [Running a Server](https://github.com/readingbat/readingbat-template/wiki/Running-a-Server)

## Build & Run Commands

| Command | Description |
|---------|-------------|
| `./gradlew build -xtest` | Compile without tests |
| `./gradlew run` | Start the dev server (port 8080) |
| `./gradlew --rerun-tasks check` | Run all tests |
| `./gradlew test --tests "ContentTests"` | Run a single test class |
| `./gradlew uberjar` | Build a fat JAR (`build/libs/server.jar`) |
| `./gradlew build --continuous -xtest` | Continuous compile on file changes |
| `./gradlew dependencyUpdates` | Check for dependency updates |

A `Makefile` is also provided with shorthand targets (`make build`, `make run`, `make tests`, etc.).

> Project metadata (`group`, `version`) is set in [`gradle.properties`](./gradle.properties).
> Dependency versions and the `testing` bundle are defined in [`gradle/libs.versions.toml`](./gradle/libs.versions.toml).

## Content Specification

Specify ReadingBat content with the ReadingBat-specific Kotlin DSL. Using the DSL does not require in-depth knowledge of
Kotlin.

ReadingBat supports challenges written in 3 languages: Python, Java and Kotlin.

Specify the content in the [src/main/kotlin/Content.kt](./src/main/kotlin/Content.kt) file.

The structure of the DSL is:
```kotlin
val content = 
  readingBatContent { 
    python {                                    // Creates a LanguageGroup object
      group("Group 1") {                        // Creates a ChallengeGroup named "Group 1"
        packageName = "group1"                  // The path of the challenges in this group
        description = "Description of **Python** Group 1" // Descriptions support markdown

        challenge("find_it") {                  // Creates a Challenge for group1/find_it.py
          returnType = BooleanType              // Challenge return type
        }

        challenge("boolean2") {                 // Creates a Challenge for group1/boolean2.py
          returnType = BooleanType              // Challenge return type
        }
        
        // Include all challenges matching the "slice*.py" filename pattern
        includeFilesWithType = "slice*.py" returns StringType  
      }
    }

    java {
      group("Group 1") {
        packageName = "group1"
        description = "Description of **Java** Group 1"

        challenge("JoinEnds") {                 // Java Return types are inferred from the code
          codingBatEquiv = "p141494"            // Will add a link to this codingbat.com challenge
        }

        challenge("ReplaceCheck")               // Creates a Challenge for group1/ReplaceCheck.java

        // Include all challenges matching the "Has*.java" filename pattern
        includeFiles = "Has*.java"
      }
    }

    kotlin {
      group("Group 1") {
        packageName = "kgroup1"
        description = "Description of **Kotlin** Group 1"

        challenge("StringLambda1") {
          returnType = StringType
        }

        // Include all challenges matching the "lambda*.kt" filename pattern
        includeFilesWithType = "lambda*.kt" returns StringType
      }
    }
  }
```

### DSL Objects

- [ReadingBatContent](https://github.com/readingbat/readingbat-template/wiki/ReadingBatContent-Objects)
- [LanguageGroup](https://github.com/readingbat/readingbat-template/wiki/LanguageGroup-Objects)
- [ChallengeGroup](https://github.com/readingbat/readingbat-template/wiki/ChallengeGroup-Objects)
- [Challenge](https://github.com/readingbat/readingbat-template/wiki/Challenge-Objects)

## Challenge Code

Challenge source files live in language-specific directories:

| Language | Directory | Example |
|----------|-----------|---------|
| Python | `python/<packageName>/` | `python/group1/find_it.py` |
| Java | `src/main/java/<packageName>/` | `src/main/java/group1/JoinEnds.java` |
| Kotlin | `src/main/kotlin/<packageName>/` | `src/main/kotlin/kgroup1/StringLambda1.kt` |

Wiki references:
[Python](https://github.com/readingbat/readingbat-template/wiki/Python-Challenges)
| [Java](https://github.com/readingbat/readingbat-template/wiki/Java-Challenges)
| [Kotlin](https://github.com/readingbat/readingbat-template/wiki/Kotlin-Challenges)

## Server Configuration

Server settings are in [`src/main/resources/application.conf`](./src/main/resources/application.conf) (HOCON format).
See the [Server Configuration](https://github.com/readingbat/readingbat-template/wiki/Server-Configuration) wiki page for details.

## Examples

The [readingbat-site](https://github.com/readingbat/readingbat-site) repo powers
[ReadingBat.com](https://readingbat.com) and combines content from multiple repos:

**Content.kt files:**
- [Top-level Content.kt](https://github.com/readingbat/readingbat-site/blob/master/src/Content.kt)
- [Java and Kotlin Content](https://github.com/readingbat/readingbat-java-content/blob/master/src/main/kotlin/Content.kt)
- [Python Content](https://github.com/readingbat/readingbat-python-content/blob/master/src/Content.kt)

**Challenge source code:**
- [Python](https://github.com/readingbat/readingbat-python-content/tree/master/python)
- [Java](https://github.com/readingbat/readingbat-java-content/tree/master/src/main/java)
- [Kotlin](https://github.com/readingbat/readingbat-java-content/tree/master/src/main/kotlin)

## More Resources

- [Kotlin Tips](https://github.com/readingbat/readingbat-template/wiki/Kotlin-Tips)
- [GitHub Tips](https://github.com/readingbat/readingbat-template/wiki/GitHub-Tips)

