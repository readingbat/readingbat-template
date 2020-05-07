import com.github.pambrose.common.util.GitHubRepo
import com.github.readingbat.dsl.ReturnType.BooleanType
import com.github.readingbat.dsl.ReturnType.StringType
import com.github.readingbat.dsl.readingBatContent


val siteContent =
  readingBatContent {
    repo = GitHubRepo(organizationName = "readingbat", repoName = "readingbat-template")

    python {
      srcPath = "python"

      group("Group1 1") {
        packageName = "group1"
        description = "This is a description of Warmup 1"

        challenge("boolean1") {
          description = "This description supports **markdown**"
          returnType = BooleanType
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "This description supports **markdown**"
        includeFilesWithType = "slice*.py" returns StringType
      }
    }

    java {

      group("Group1 1") {
        packageName = "group1"
        description = "This description supports **markdown**"

        challenge("JoinEnds") {
          description = "This description supports **markdown**"
        }
      }

      group("Group 2") {
        packageName = "group2"
        description = "This description supports **markdown**"
        includeFiles = "Has*.*"
      }

    }

    kotlin {

    }

  }