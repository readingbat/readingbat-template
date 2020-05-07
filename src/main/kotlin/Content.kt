import com.github.pambrose.common.util.GitHubRepo
import com.github.readingbat.dsl.ReturnType.BooleanType
import com.github.readingbat.dsl.ReturnType.StringType
import com.github.readingbat.dsl.readingBatContent


val siteContent =
  readingBatContent {
    python {
      repo = GitHubRepo("readingbat", "readingbat-template")
      srcPath = "python"

      group("Warmup 1") {
        packageName = "warmup1"
        description = "This is a description of Warmup 1"
        includeFilesWithType = "slice*.py" returns StringType

        challenge("boolean1") {
          description = "This description supports **markdown**"
          returnType = BooleanType
        }

      }
    }

    java {
      repo = GitHubRepo("readingbat", "readingbat-template")

    }

    kotlin {
      repo = GitHubRepo("readingbat", "readingbat-template")

    }

  }