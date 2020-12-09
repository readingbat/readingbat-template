import com.github.pambrose.common.util.*
import com.github.readingbat.TestSupport.answerAllWith
import com.github.readingbat.TestSupport.answerAllWithCorrectAnswer
import com.github.readingbat.TestSupport.answerFor
import com.github.readingbat.TestSupport.forEachAnswer
import com.github.readingbat.TestSupport.forEachChallenge
import com.github.readingbat.TestSupport.forEachGroup
import com.github.readingbat.TestSupport.forEachLanguage
import com.github.readingbat.TestSupport.javaChallenge
import com.github.readingbat.TestSupport.kotlinChallenge
import com.github.readingbat.TestSupport.pythonChallenge
import com.github.readingbat.TestSupport.shouldHaveAnswer
import com.github.readingbat.TestSupport.shouldNotHaveAnswer
import com.github.readingbat.TestSupport.testModule
import com.github.readingbat.common.*
import com.github.readingbat.common.Property.*
import com.github.readingbat.dsl.*
import com.github.readingbat.posts.*
import com.github.readingbat.posts.AnswerStatus.*
import com.github.readingbat.server.*
import io.kotest.core.spec.style.*
import io.kotest.matchers.*
import io.kotest.matchers.string.*
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.testing.*

class ContentTests : StringSpec({

  "Test all challenges" {
    withTestApplication({ testModule(content) }) {

      content.forEachLanguage {
        forEachGroup {
          forEachChallenge {
            answerAllWith(this@withTestApplication, "") {
              answerStatus shouldBe NOT_ANSWERED
              hint.shouldBeBlank()
            }

            answerAllWith(this@withTestApplication, "wrong answer") {
              answerStatus shouldBe INCORRECT
            }

            answerAllWithCorrectAnswer(this@withTestApplication) {
              answerStatus shouldBe CORRECT
              hint.shouldBeBlank()
            }
          }
        }
      }
    }
  }

  "Test with correct answers" {
    withTestApplication({ testModule(content) }) {
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
    withTestApplication({ testModule(content) }) {
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