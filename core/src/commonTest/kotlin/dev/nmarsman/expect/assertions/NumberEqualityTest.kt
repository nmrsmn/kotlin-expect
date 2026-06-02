package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person
import kotlin.test.DefaultAsserter.fail

val NumberEqualityTest by testSuite(name = "Number equality tests") {
    testSuite(name = "Double equality") {
        test(name = "Double equality check should pass if the expected value is within the tolerance of subject") {
            expectThat(subject = 5.001)
                .isEqualTo(expected = 5.0, tolerance = 0.01)
        }

        test(name = "Double equality check should not pass if the expected value is outside the tolerance of subject") {
            expectThrows<AssertionFailedException> {
                expectThat(subject = 5.001)
                    .isEqualTo(expected = 5.0, tolerance = 0.0001)
            }
        }
    }

    testSuite(name = "Float equality") {
        test(name = "Float equality check should pass if the expected value is within the tolerance of subject") {
            expectThat(subject = 5.001f)
                .isEqualTo(expected = 5.0f, tolerance = 0.01f)
        }

        test(name = "Float equality check should not pass if the expected value is outside the tolerance of subject") {
            expectThrows<AssertionFailedException> {
                expectThat(subject = 5.001f)
                    .isEqualTo(expected = 5.0f, tolerance = 0.0001f)
            }
        }
    }
}
