[![Kotlin](https://img.shields.io/badge/%20language-Kotlin-red.svg)](https://kotlinlang.org/)

# ReadingBat Template

[![Open in Gitpod](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/readingbat/readingbat-template)
[![Run on Repl.it](https://repl.it/badge/github/readingbat/readingbat-template)](https://repl.it/github/readingbat/readingbat-template)

The underlying framework for this is the [readingbat-core](https://github.com/readingbat/readingbat-core) repo.

## Setup

Click on the [![](docs/template_button.png)](https://github.com/readingbat/readingbat-template/generate) 
button above to clone the template repo and create your own ReadingBat content.

## Running

The server can be run in two modes: development and production.

Running with redis enabled is optional. It is unnecessary for creating content, but 
highly advisable in production. 

### Development Mode

Development mode allows you to edit content and see the updates in the browser without
restarting the server.

To enable development mode with :

1) Uncomment the `ktor.deployment.watch` variable in [application.conf](./src/main/resources/application.conf#L31).

2) Run the server with: `make run` in a terminal window.

3) Compile the code continuously with: `make cc` in a different terminal window. This step is optional.
Without it, you will have to restart the server after changes to the content description file. 

4) View your content at http://0.0.0.0:8080

### Production Mode

1) Comment out the `ktor.deployment.watch` variable in [application.conf](./src/main/resources/application.conf#L31)

2) Run the server locally with: `make uber`

There are numerous deployment hosting options inclusing: docker, Heroku, repl.it, gitpod.io

## Content Specification

ReadingBat content is described with a Kotlin DSL. Using the DSL requires very little knowledge of Kotlin.

ReadingBat supports challenges written in 3 languages: Python, Java and Kotlin.

The content is specified in [src/kotlin/Content.kt](./src/kotlin/Content.kt).

The high-level structure of the DSL is:
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

### ReadingBatContent

A *Content.kt* DSL description defines a variable named **content** of type `ReadingBatContent`.
The value is created with a `readingBatContent {}` call.

#### ReadingBatContent Variables:
| Name            | Default                | Description                                                |
|-----------------|------------------------|------------------------------------------------------------|
| repo            | `FileSystemSource("./")` | Default source for the challenges    |
| branchName      | `"master"`               | Default Github branch name for the sources |
| cacheChallenges | `!isProduction()`        | Determines if challenges are chanced after being read | 


### LanguageGroups

A `readingBatContent` can have 3 `LanguangeGroups` declarations: python, java, and kotlin.
Each is created with `python {}`, `java {}`, `kotlin {}` calls.

#### LanguageGroup Variables:
| Name            | Default                       | Description                                                |
|-----------------|-------------------------------|------------------------------------------------------------|
| repo            | ReadingBatContent.repo        | Languages-specific source for the challenges    |
| branchName      | ReadingBatContent.branchName  | Languages-specific Github branch name for the sources |
| srcPath         | See below                     |

#### LanguageGroup srcPath Defaults

| Language | Default srcPath   |
|----------|-------------------|
| python   | `"python"`          |
| java     | `"src/main/java"`   |
| kotlin   | `"src/main/kotlin"` |


### ChallengeGroups

### Challenges


Python and Kotlin challenges require a `returnType` value. The `returnType` value is inferred at runtime for Java challenges.

Valid types for `returnType` include:
  BooleanType, IntType, StringType, BooleanArrayType, IntArrayType, StringArrayType, BooleanListType,
  IntListType, StringListType;
 



The `includeFiles` or `includeFilesWithType` properties require a 
[Github personal access token](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line).