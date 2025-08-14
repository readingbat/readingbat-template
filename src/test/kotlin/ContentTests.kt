import com.github.pambrose.common.util.*
import com.github.readingbat.kotest.TestSupport.answerAllWith
import com.github.readingbat.kotest.TestSupport.answerAllWithCorrectAnswer
import com.github.readingbat.kotest.TestSupport.answerFor
import com.github.readingbat.kotest.TestSupport.forEachAnswer
import com.github.readingbat.kotest.TestSupport.forEachChallenge
import com.github.readingbat.kotest.TestSupport.forEachGroup
import com.github.readingbat.kotest.TestSupport.forEachLanguage
import com.github.readingbat.kotest.TestSupport.javaChallenge
import com.github.readingbat.kotest.TestSupport.kotlinChallenge
import com.github.readingbat.kotest.TestSupport.pythonChallenge
import com.github.readingbat.kotest.TestSupport.shouldHaveAnswer
import com.github.readingbat.kotest.TestSupport.shouldNotHaveAnswer
import com.github.readingbat.kotest.TestSupport.testModule
import com.github.readingbat.posts.AnswerStatus.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.string.*
import io.ktor.server.testing.*

class ContentTests : StringSpec({

  "Test all challenges" {
    testApplication {
      application {
        testModule(content)
      }

      content.forEachLanguage {
        forEachGroup {
          forEachChallenge {
            answerAllWith(this@testApplication, "") {
              answerStatus shouldBe NOT_ANSWERED
              hint.shouldBeBlank()
            }

            answerAllWith(this@testApplication, "wrong answer") {
              answerStatus shouldBe INCORRECT
            }

            answerAllWithCorrectAnswer(this@testApplication) {
              answerStatus shouldBe CORRECT
              hint.shouldBeBlank()
            }
          }
        }
      }
    }
  }

  "Test with correct answers" {
    testApplication {
      application {
        testModule(content)
      }

      content.forEachLanguage {
        forEachGroup {
          forEachChallenge {
            forEachAnswer {
              it shouldHaveAnswer correctAnswers[it.index]
            }
          }
        }
      }
    }
  }

  "Test individual challenges" {
    testApplication {
      application {
        testModule(content)
      }

      content.pythonChallenge("Group 1", "find_it") {
        answerFor(0) shouldNotHaveAnswer "true"
        answerFor(1) shouldNotHaveAnswer "false"

        answerFor(0) shouldHaveAnswer "False"
        answerFor(1) shouldHaveAnswer "True"
        answerFor(2) shouldHaveAnswer "False"
        answerFor(3) shouldHaveAnswer "True"
        answerFor(4) shouldHaveAnswer "False"
      }

      content.javaChallenge("Group 1", "JoinEnds") {
        answerFor(0) shouldNotHaveAnswer "AB".toDoubleQuoted()

        answerFor(0) shouldHaveAnswer "aB".toDoubleQuoted()
      }

      content.kotlinChallenge("Group 1", "StringLambda1") {
        answerFor(0) shouldNotHaveAnswer "a0".toDoubleQuoted()

        answerFor(0) shouldHaveAnswer "0a".toDoubleQuoted()
      }
    }
  }
})