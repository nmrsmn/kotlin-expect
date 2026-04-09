package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import kotlin.test.DefaultAsserter.fail

val AnyAssertionTest by testSuite(
    displayName = "Any assertion tests",
) {
    testSuite(name = "subject 'is A' assertions") {
        test(name = "isA should pass if the subject is an instance of the expected type") {
            expectThat("value")
                .isA<String>()
        }

        test(name = "isA should pass if the subject is an instance of a subtype of the expected type") {
            expectThat(1L)
                .isA<Number>()
        }

        test(name = "isA should fail if the subject is not an instance of the expected type") {
            try {
                expectThat("value")
                    .isA<Number>()

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }

        test(name = "isA should fail if the subject is null") {
            try {
                expectThat(null)
                    .isA<String>()

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }

    testSuite(name = "subject 'is null' assertions") {
        test("isNull should pass if the subject is null") {
            expectThat(null)
                .isNull()
        }

        test("isNull should fail if the subject is not null") {
            try {
                expectThat("value" as String?)
                    .isNull()

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }

    testSuite(name = "`is not null` assertions") {
        test("Passes if the subject is not null") {
            expectThat("subject" as String?)
                .isNotNull()
        }

        test("Fails if the subject is null") {
            expectThrows<AssertionError> {
                expectThat(null)
                    .isNotNull()
            }
        }
    }
}
