import com.pambrose.common.util.toDoubleQuoted
import com.readingbat.kotest.TestSupport.answerAllWith
import com.readingbat.kotest.TestSupport.answerAllWithCorrectAnswer
import com.readingbat.kotest.TestSupport.answerFor
import com.readingbat.kotest.TestSupport.forEachAnswer
import com.readingbat.kotest.TestSupport.forEachChallenge
import com.readingbat.kotest.TestSupport.forEachGroup
import com.readingbat.kotest.TestSupport.forEachLanguage
import com.readingbat.kotest.TestSupport.initTestProperties
import com.readingbat.kotest.TestSupport.javaChallenge
import com.readingbat.kotest.TestSupport.kotlinChallenge
import com.readingbat.kotest.TestSupport.pythonChallenge
import com.readingbat.kotest.TestSupport.shouldHaveAnswer
import com.readingbat.kotest.TestSupport.shouldNotHaveAnswer
import com.readingbat.kotest.TestSupport.testModule
import com.readingbat.posts.AnswerStatus.CORRECT
import com.readingbat.posts.AnswerStatus.INCORRECT
import com.readingbat.posts.AnswerStatus.NOT_ANSWERED
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeBlank
import io.ktor.server.testing.*

class ContentTests : StringSpec() {
  init {
    beforeEach { initTestProperties() }

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
  }
}
