package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val ComparableFailureFormattingTest by testSuite(
    displayName = "Comparable failure formatting tests",
) {
    test(name = "Formats the failure message of `isGreaterThan` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(12)
                .isGreaterThan(15)
        }.hasMessage(
            """
                |▼ Expect that 12:
                |   ✗ is greater than 15
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isLessThan` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(12)
                .isLessThan(10)
        }.hasMessage(
            """
                |▼ Expect that 12:
                |   ✗ is less than 10
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isGreaterThanOrEqualTo` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(12)
                .isGreaterThanOrEqualTo(15)
        }.hasMessage(
            """
                |▼ Expect that 12:
                |   ✗ is greater than or equal to 15
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isLessThanOrEqualTo` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(12)
                .isLessThanOrEqualTo(10)
        }.hasMessage(
            """
                |▼ Expect that 12:
                |   ✗ is less than or equal to 10
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isIn` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(12)
                .isIn(0..10)
        }.hasMessage(
            """
                |▼ Expect that 12:
                |   ✗ is in the range 0..10
            """.trimMargin(),
        )
    }
}
