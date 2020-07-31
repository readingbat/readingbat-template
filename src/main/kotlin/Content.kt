import com.github.pambrose.common.util.*
import com.github.pambrose.common.util.OwnerType.*
import com.github.readingbat.dsl.*
import com.github.readingbat.dsl.ReturnType.*

val content =
  readingBatContent {
    repo = if (isProduction()) GitHubRepo(Organization, "readingbat", "readingbat-template") else FileSystemSource("./")
    // After cloning this template, you need to change the GitHubRepo args to point to your content.
    // If user gsmith cloned the template into a repo named my-readingbat-content, the args would look like this:
    // repo = if (isProduction()) GitHubRepo(User, "gsmith", "my-readingbat-content") else FileSystemSource("./")

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

        challenge("JoinEnds") {                 // Java Return types are inferred from the code
          codingBatEquiv = "p141494"            // Will add a link to this codingbat.com challenge
        }

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