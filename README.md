[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

# ReadingBat Template

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/readingbat/readingbat-template)
[![Run on Repl.it](https://repl.it/badge/github/readingbat/readingbat-template)](https://repl.it/github/readingbat/readingbat-template)

The underlying framework for this template is the [readingbat-core](https://github.com/readingbat/readingbat-core) repo.

## Setup

Click on the [![](docs/template_button.png)](https://github.com/readingbat/readingbat-template/generate) 
button above to clone the template repo and create your own ReadingBat content.

Open the cloned template with [IntelliJ](https://www.jetbrains.com/idea/), [VSCode](https://code.visualstudio.com) 
or [Gitpod.io](https://gitpod.io). 
Install and enable the appropriate Kotlin plugin for your dev environment.

Install Redis (optional).

## [Running a Server](https://github.com/readingbat/readingbat-template/wiki/Running-a-Server)

## [Server Configuration](https://github.com/readingbat/readingbat-template/wiki/Server-Configuration)

## Content Specification

Specify ReadingBat content with a ReadingBat-specific Kotlin DSL. 
Using the DSL requires very little knowledge of Kotlin.

ReadingBat supports challenges written in 3 languages: Python, Java and Kotlin.

Specify the content in the [src/main/kotlin/Content.kt](./src/main/kotlin/Content.kt) file.

The structure of the DSL is:
```kotlin
val content = 
  readingBatContent { 
    python {                                    // Creates a LanguageGroup object
      group("Group 1") {                        // Creates a ChallengeGroup named "Group 1"
        packageName = "group1"                  // The path of the challenges in this group
        description = "Group description"       // Descriptions support markdown

        challenge("boolean1") {                 // Creates a Challenge for group1/boolean1.py
          description = "Challenge description" // Optional description of the Challenge
          returnType = BooleanType              // Challenge return type
        }

        challenge("boolean2") {                 // Creates a Challenge for group1/boolean2.py
          description = "Challenge description" // Optional description of the Challenge
          returnType = BooleanType              // Challenge return type
        }
      }     

      group("Group 2") {
        packageName = "group2"
        description = "Description of Group 2"  
        includeFilesWithType = "slice*.py" returns StringType
      }
    }

    java {
      group("Group1 1") {
        packageName = "group1"
        description = "Group description"

        challenge("JoinEnds") {
          description = "Challenge description"
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "Group description"
        includeFiles = "Has*.java"
      }
    }

    kotlin {
      group("Group1 1") {
        packageName = "kgroup1"
        description = "Group description"

        challenge("StringLambda1") {
          description = "Challenge description"
          returnType = StringType
        }
      }

      group("Group 2") {
        packageName = "kgroup2"
        includeFilesWithType = "lambda*.kt" returns StringType
      }
    }
  }
```

### DSL Objects

* [ReadingBatContent](https://github.com/readingbat/readingbat-template/wiki/ReadingBatContent-Objects)
* [LanguageGroup](https://github.com/readingbat/readingbat-template/wiki/LanguageGroup-Objects)
* [ChallengeGroup](https://github.com/readingbat/readingbat-template/wiki/ChallengeGroup-Objects)
* [Challenge](https://github.com/readingbat/readingbat-template/wiki/Challenge-Objects)

## Challenge Code
* [Python Code](https://github.com/readingbat/readingbat-template/wiki/Python-Challenges)
* [Java Code](https://github.com/readingbat/readingbat-template/wiki/Java-Challenges)
* [Kotlin Code](https://github.com/readingbat/readingbat-template/wiki/Kotlin-Challenges)
