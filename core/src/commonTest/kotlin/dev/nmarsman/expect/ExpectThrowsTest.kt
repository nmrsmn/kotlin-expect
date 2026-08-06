package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.isEqualTo
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

val ExpectThrowsTest by testSuite {
    test(name = "Should pass when expected exception is thrown") {
        expectThrows<IllegalStateException> {
            error("Expected exception")
        }
    }

    test(name = "Should pass when sub-class of expected exception is thrown") {
        expectThrows<RuntimeException> {
            error("Expected exception")
        }
    }

    test(name = "Should fail when no exception is thrown") {
        expectThrows<AssertionError> {
            expectThrows<IllegalStateException> { }
        }.let {
            expectThat(it.subject.message)
                .isEqualTo(
                    """
                        |▼ Expect that IllegalStateException:
                        |   ✗ is thrown,
                        |     but no exception is thrown
                    """.trimMargin(),
                )
        }
    }

    test(name = "Should fail when wrong exception is thrown") {
        expectThrows<AssertionError> {
            expectThrows<NullPointerException> {
                error("Unexpected exception")
            }
        }.let {
            expectThat(it.subject.message)
                .isEqualTo(
                    """
                        |▼ Expect that NullPointerException:
                        |   ✗ is thrown,
                        |     but threw IllegalStateException
                    """.trimMargin(),
                )
        }
    }

    test(name = "Should allow suspending function to be used as method") {
        expectThrows<TimeoutCancellationException> {
            test()
        }
    }
}

suspend fun test() = withTimeout(1.seconds) {
    delay(2.seconds)
}
