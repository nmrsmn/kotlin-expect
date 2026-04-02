package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import kotlin.test.DefaultAsserter.fail

val AnyAssertionTest by testSuite {
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
