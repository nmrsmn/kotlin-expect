package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val ThrowableAssertionTest by testSuite(
    displayName = "Throwable assertion tests",
) {
    testSuite(name = "`hasMessage` assertions") {
        test(name = "Passes if the subject has the expected message") {
            expectThrows<IllegalStateException> { error("Expected message") }
                .hasMessage("Expected message")
        }

        test(name = "Fails if the subject does not have the expected message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Actual message") }
                    .hasMessage("Expected message")
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessage("Expected message")
            }
        }
    }
}
