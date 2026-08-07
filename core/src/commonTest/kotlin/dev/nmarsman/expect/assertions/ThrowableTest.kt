package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val ThrowableAssertionTest by testSuite(
    name = "Throwable assertion tests",
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

    testSuite(name = "`hasMessageContaining(substring)` assertions") {
        test(name = "Passes if the subject has a message containing the expected substring") {
            expectThrows<IllegalStateException> { error("Expected message") }
                .hasMessageContaining("Expected")
        }

        test(name = "Fails if the subject does not have a message containing the expected substring") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Actual message") }
                    .hasMessageContaining("Expected")
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessageContaining("Expected")
            }
        }
    }

    testSuite(name = "`hasMessageContaining(regex)` assertions") {
        test(name = "Passes if the subject has a message containing the expected regex") {
            expectThrows<IllegalStateException> { error("Expected message") }
                .hasMessageContaining("^Expected".toRegex())
        }

        test(name = "Fails if the subject does not have a message containing the expected regex") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Actual message") }
                    .hasMessageContaining("^Expected".toRegex())
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessageContaining("^Expected".toRegex())
            }
        }
    }

    testSuite(name = "`hasMessageStartingWith` assertions") {
        test(name = "Passes if the subject has a message starting with the expected substring") {
            expectThrows<IllegalStateException> { error("Expected message") }
                .hasMessageStartingWith("Expected")
        }

        test(name = "Fails if the subject does not have a message starting with the expected substring") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Actual message") }
                    .hasMessageStartingWith("Expected")
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessageStartingWith("Expected")
            }
        }
    }

    testSuite(name = "`hasMessageEndingWith` assertions") {
        test(name = "Passes if the subject has a message ending with the expected substring") {
            expectThrows<IllegalStateException> { error("Message expected") }
                .hasMessageEndingWith("expected")
        }

        test(name = "Fails if the subject does not have a message ending with the expected substring") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Message actual") }
                    .hasMessageEndingWith("expected")
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessageEndingWith("expected")
            }
        }
    }

    testSuite(name = "`hasMessageMatching` assertions") {
        test(name = "Passes if the subject has a message matching the expected regex") {
            expectThrows<IllegalStateException> { error("Expected message") }
                .hasMessageMatching("^Expected message$".toRegex())
        }

        test(name = "Fails if the subject does not have a message matching the expected regex") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { error("Actual message") }
                    .hasMessageMatching("^Expected message$".toRegex())
            }
        }

        test(name = "Fails if the subject does have a `null` message") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasMessageMatching("^Expected message$".toRegex())
            }
        }
    }

    testSuite(name = "`hasAnyCause` assertions") {
        test(name = "Passes if the subject has a cause") {
            expectThrows<IllegalStateException> {
                val cause = RuntimeException("Cause")
                throw IllegalStateException("Expected message", cause)
            }.hasAnyCause()
        }

        test(name = "Fails if the subject does not have a cause") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasAnyCause()
            }
        }
    }

    testSuite(name = "`hasCause` assertions") {
        test(name = "Passes if the subject has the supplied cause") {
            expectThrows<IllegalStateException> {
                val cause = RuntimeException("Cause")
                throw IllegalStateException("Some message", cause)
            }.hasCause<RuntimeException>()
        }

        test(name = "Fails if the subject does not have a cause") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> { throw IllegalStateException() }
                    .hasCause<RuntimeException>()
            }
        }

        test(name = "Fails if the subject does not have a cause of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> {
                    val cause = RuntimeException("Cause")
                    throw IllegalStateException("Some message", cause)
                }.hasCause<AssertionError>()
            }
        }
    }

    testSuite(name = "`hasNoCause` assertions") {
        test(name = "Passes if the subject has no cause") {
            expectThrows<IllegalStateException> { error("Some message") }
                .hasNoCause()
        }

        test(name = "Fails if the subject does have a cause") {
            expectThrows<AssertionFailedException> {
                expectThrows<IllegalStateException> {
                    val cause = RuntimeException("Cause")
                    throw IllegalStateException("Some message", cause)
                }.hasNoCause()
            }
        }
    }
}
