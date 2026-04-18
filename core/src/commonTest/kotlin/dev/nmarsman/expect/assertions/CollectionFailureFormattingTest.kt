package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val CollectionFailureFormattingTest by testSuite(
    displayName = "Collection failure formatting tests",
) {
    test(name = "Formats the failure message of `hasSize` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(listOf("item1", "item2"))
                .hasSize(3)
        }.hasMessage(
            """
                |▼ Expect that ["item1", "item2"]:
                |   ✗ has size 3
                |     but was: 2
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isEmpty` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(listOf("item1", "item2"))
                .isEmpty()
        }.hasMessage(
            """
                |▼ Expect that ["item1", "item2"]:
                |   ✗ is empty
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isNotEmpty` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(emptyList<String>())
                .isNotEmpty()
        }.hasMessage(
            """
                |▼ Expect that []:
                |   ✗ is not empty
            """.trimMargin(),
        )
    }
}
