import com.github.pambrose.common.util.FileSystemSource
import com.github.readingbat.dsl.ReturnType.BooleanType
import com.github.readingbat.dsl.ReturnType.StringType
import com.github.readingbat.dsl.readingBatContent

val content =
  readingBatContent {
    //repo = GitHubRepo(organizationName = "readingbat", repoName = "readingbat-template")
    repo = FileSystemSource("./")
    cacheChallenges = false

    python {
      srcPath = "python"

      group("Group1 1") {
        packageName = "group1"
        description = "This is a description of Warmup 1"

        challenge("boolean1") {
          description = "Descriptions support **markdown**"
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

      group("Group1 1") {
        packageName = "group1"
        description = "A description"

        challenge("JoinEnds") {
          description = "This description supports **markdown**"
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "A description"
        includeFiles = "Has*.*"
      }

    }

    kotlin {

    }

  }