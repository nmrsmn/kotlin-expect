package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import kotlin.test.DefaultAsserter.fail

val AnyAssertionTest by testSuite(
    displayName = "Any assertion tests",
) {
    testSuite(name = "`isA` assertions") {
        test(name = "Passes if the subject is an instance of the expected type") {
            expectThat("value")
                .isA<String>()
        }

        test(name = "Passes if the subject is an instance of a subtype of the expected type") {
            expectThat(1L)
                .isA<Number>()
        }

        test(name = "Fails if the subject is not an instance of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat("value")
                    .isA<Number>()
            }
        }

        test(name = "Fails if the subject is `null`") {
            expectThrows<AssertionFailedException> {
                expectThat(null)
                    .isA<String>()
            }
        }
    }

    testSuite(name = "`isNotA` assertions") {
        test(name = "Passes if the subject is not an instance of the expected type") {
            expectThat("value")
                .isNotA<Number>()
        }

        test(name = "Passes if the subject is `null`") {
            expectThat(null)
                .isNotA<String>()
        }

        test(name = "Fails if the subject is an instance of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat("value")
                    .isNotA<String>()
            }
        }

        test(name = "Fails if the subject is an instance of a subtype of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat(1L)
                    .isNotA<Number>()
            }
        }
    }

    testSuite(name = "`isNull` assertions") {
        test("isNull should pass if the subject is null") {
            expectThat(null)
                .isNull()
        }

        test("isNull should fail if the subject is not null") {
            expectThrows<AssertionFailedException> {
                expectThat("value" as String?)
                    .isNull()
            }
        }
    }

    testSuite(name = "`isNotNull` assertions") {
        test("Passes if the subject is not null") {
            expectThat("subject" as String?)
                .isNotNull()
        }

        test("Fails if the subject is null") {
            expectThrows<AssertionFailedException> {
                expectThat(null)
                    .isNotNull()
            }
        }
    }
}
