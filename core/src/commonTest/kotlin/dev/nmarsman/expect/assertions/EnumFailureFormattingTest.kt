package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

private enum class Color {
    RED,
    GREEN,
    BLUE,
}

val EnumFailureFormattingTest by testSuite(
    displayName = "Enum failure formatting tests",
) {
    test(name = "Formats the failure message of `isOneOf` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(Color.BLUE)
                .isOneOf(Color.RED, Color.GREEN)
        }.hasMessage(
            """
                |▼ Expect that BLUE:
                |   ✗ is one of RED, GREEN
            """.trimMargin(),
        )
    }
}
