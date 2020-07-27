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
        description = "Description of **Python** Group 1"

        challenge("find_it") {
          returnType = BooleanType
        }

        // Include all challenges matching the "slice*.py" filename pattern
        includeFilesWithType = "slice*.py" returns StringType
      }
    }

    java {

      group("Group 1") {
        packageName = "group1"
        description = "Description of **Java** Group 1"

        challenge("JoinEnds")

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

        // Include all challenges matching the "Int*.kt" filename pattern
        includeFilesWithType = "Int*.kt" returns IntType
      }
    }
  }