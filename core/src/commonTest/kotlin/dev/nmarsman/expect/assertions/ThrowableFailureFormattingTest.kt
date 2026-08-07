package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val ThrowableFailureFormattingTest by testSuite(
    name = "Throwable failure formatting tests",
) {
    test(name = "Formats the failure message of `hasMessage` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Actual message") }
                .hasMessage("Expected message")
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message "Expected message"
                |     but message was: "Actual message"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasMessageContaining(substring)` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Actual message") }
                .hasMessageContaining("Expected")
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message containing "Expected"
                |     but message was: "Actual message"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasMessageContaining(regex)` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Actual message") }
                .hasMessageContaining("^Expected".toRegex())
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message containing /^Expected/
                |     but message was: "Actual message"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasMessageStartingWith` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Actual message") }
                .hasMessageStartingWith("Expected")
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message starting with "Expected"
                |     but message was: "Actual message"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasMessageEndingWith` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Message actual") }
                .hasMessageEndingWith("expected")
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message ending with "expected"
                |     but message was: "Message actual"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasMessageMatching` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Actual message") }
                .hasMessageMatching("^Expected message$".toRegex())
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has message matching /^Expected message$/
                |     but message was: "Actual message"
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasAnyCause` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Message actual") }
                .hasAnyCause()
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has a cause
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasCause` assertion correctly - no cause") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> { error("Message actual") }
                .hasCause<RuntimeException>()
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has cause of type: RuntimeException
                |     but was: null
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasCause` assertion correctly - different cause") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> {
                val cause = RuntimeException()
                throw IllegalStateException("Some message", cause)
            }.hasCause<AssertionFailedException>()
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has cause of type: AssertionFailedException
                |     but was: RuntimeException
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `hasNoCause` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThrows<IllegalStateException> {
                val cause = RuntimeException()
                throw IllegalStateException("Some message", cause)
            }.hasNoCause()
        }.hasMessage(
            """
                |▼ Expect that IllegalStateException:
                |   ✓ is thrown,
                |
                |   ✗ has no cause
                |     but cause was of type: RuntimeException
            """.trimMargin(),
        )
    }
}
