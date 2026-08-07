package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectDoesNotThrow
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.hasMessage
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.exception.AssertionFailedException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.seconds

val ExpectDoesNotThrowTest by testSuite {
    test(name = "Should pass when no exception is thrown") {
        expectDoesNotThrow { 1 + 1 }
            .isEqualTo(2)
    }

    test(name = "Should fail when an exception is thrown") {
        expectThrows<AssertionFailedException> {
            expectDoesNotThrow<AssertionError> {
                error("Some error!")
            }
        }.also {
            expectThat(it.subject.message)
                .isEqualTo(
                    """
                        |▼ Expect that AssertionError:
                        |   ✗ does not throw,
                        |     but threw IllegalStateException
                    """.trimMargin(),
                )
        }
    }
}
