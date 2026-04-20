package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import kotlin.getValue

val MapFailureFormattingTest by testSuite(
    displayName = "Map failure formatting tests",
) {
    test(name = "Formats the failure message of `hasSize` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .hasSize(3)
        }.hasMessage(
            """
                |▼ Expect that {1="item1", 2="item2"}:
                |   ✗ has size 3
                |     but was: 2
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isEmpty` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .isEmpty()
        }.hasMessage(
            """
                |▼ Expect that {1="item1", 2="item2"}:
                |   ✗ is empty
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `isNotEmpty` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(emptyMap<Int, String>())
                .isNotEmpty()
        }.hasMessage(
            """
                |▼ Expect that {}:
                |   ✗ is not empty
            """.trimMargin(),
        )
    }

    test(name = "Formats the failure message of `containsKey` assertion correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(emptyMap<Int, String>())
                .containsKey(1)
        }.hasMessage(
            """
                |▼ Expect that {}:
                |   ✗ has an entry with the key 1
            """.trimMargin(),
        )
    }
}
