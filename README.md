[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

# ReadingBat Template

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/readingbat/readingbat-template)
[![Run on Repl.it](https://repl.it/badge/github/readingbat/readingbat-template)](https://repl.it/github/readingbat/readingbat-template)

The underlying framework for this template is the [readingbat-core](https://github.com/readingbat/readingbat-core) repo.

## Setup

Click on the [![](docs/template_button.png)](https://github.com/readingbat/readingbat-template/generate) 
button above to clone the template repo and create your own ReadingBat content.

## Development Environment 

Open the cloned template with [IntelliJ](https://www.jetbrains.com/idea/), [VSCode](https://code.visualstudio.com) 
or [Gitpod.io](https://gitpod.io). 
Install and enable the appropriate Kotlin plugin for your dev environment.

Install Redis locally.

## [Running](https://github.com/readingbat/readingbat-template/wiki/Running)

## [Server Configuration](https://github.com/readingbat/readingbat-template/wiki/Server-Configuration)

## Content Specification

Specify ReadingBat content with a ReadingBat-specific Kotlin DSL. 
Using the DSL requires very little knowledge of Kotlin.

ReadingBat supports challenges written in 3 languages: Python, Java and Kotlin.

Spcify the content in the [src/main/kotlin/Content.kt](./src/main/kotlin/Content.kt) file.

The structure of the DSL is:
```kotlin
val content = 
  readingBatContent { 
    python {
      group("Group 1") {                        // Declare each challenge group
        packageName = "group1"                  // The path of the challenges in this group
        description = "Group description"       // Descriptions support markdown

        challenge("boolean1") {                 // Declare each challenge
          description = "Challenge description" // Optional
          returnType = BooleanType              // Required for python challenges
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

### Declarations

* [ReadingBatContent](https://github.com/readingbat/readingbat-template/wiki/ReadingBatContent-Declarations)
* [LanguageGroup](https://github.com/readingbat/readingbat-template/wiki/LanguageGroup-Declarations)
* [ChallengeGroup](https://github.com/readingbat/readingbat-template/wiki/ChallengeGroup-Declarations)
* [Challenge](https://github.com/readingbat/readingbat-template/wiki/Challenge-Declarations)


