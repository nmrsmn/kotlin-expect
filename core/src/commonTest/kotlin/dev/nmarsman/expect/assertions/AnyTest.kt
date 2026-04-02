package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import kotlin.test.DefaultAsserter.fail

val AnyAssertionTest by testSuite {
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

                // Doesn't work properly until library implements AssertionFailedError from opentest4j
                fail("Test should have thrown")
            } catch (_: AssertionError) {
                // Test should have thrown
            }
        }

        test(name = "isA should fail if the subject is null") {
            try {
                expectThat(null)
                    .isA<String>()

                // Doesn't work properly until library implements AssertionFailedError from opentest4j
                fail("Test should have thrown")
            } catch (_: AssertionError) {
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

                // Doesn't work properly until library implements AssertionFailedError from opentest4j
                fail("Test should have thrown")
            } catch (_: AssertionError) {
                // Test should have thrown
            }
        }
    }
}
