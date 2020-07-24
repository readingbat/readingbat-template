import com.github.pambrose.common.util.FileSystemSource
import com.github.pambrose.common.util.GitHubRepo
import com.github.readingbat.dsl.ReturnType.*
import com.github.readingbat.dsl.isProduction
import com.github.readingbat.dsl.readingBatContent

val content =
  readingBatContent {
    repo = if (isProduction()) GitHubRepo("readingbat", "readingbat-template") else FileSystemSource("./")

    python {

      group("Group 1") {
        packageName = "group1"
        description = "This is a description of Group 1"

        challenge("find_it") {
          returnType = BooleanType
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "A description"
        includeFilesWithType = "slice*.py" returns StringType
      }
    }

    java {

      group("Group 1") {
        packageName = "group1"
        description = "A description"

        challenge("JoinEnds") {
          // Descriptions in the code are overridden by those in Content.kt
          description = "This description supports **markdown**"
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "A description"
        includeFiles = "Has*.java"
      }
    }

    kotlin {

      group("Group 1") {
        packageName = "kgroup1"
        description = "This is a description of Group 1"

        challenge("StringLambda1") {
          returnType = StringType
        }
      }

      group("Group 2") {
        packageName = "kgroup1"
        includeFilesWithType = "Int*.kt" returns IntType
      }
    }
  }