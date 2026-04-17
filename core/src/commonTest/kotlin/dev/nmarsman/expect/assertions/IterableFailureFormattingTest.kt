package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val IterableFailureFormattingTest by testSuite(
    displayName = "Iterable failure formatting tests",
) {
    testSuite(name = "`contains` failure formatting") {
        test(name = "Missing the only checked element when the subject has one element") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1"))
                    .contains("item3")
            }.hasMessage(
                """
                    |▼ Expect that ["item1"]:
                    |   ✗ contains the elements ["item3"]
                """.trimMargin(),
            )
        }

        test(name = "Missing the only checked element when the subject has multiple elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .contains("item3")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ contains the elements ["item3"]
                """.trimMargin(),
            )
        }

        test(name = "Missing one of the checked elements when the subject has multiple elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .contains("item3", "item1")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ contains the elements ["item3", "item1"]:
                    |      ✗ contains "item3"
                    |
                    |      ✓ contains "item1"
                """.trimMargin(),
            )
        }
    }

    testSuite(name = "`doesNotContain` failure formatting") {
        test(name = "Containing the only checked element when the subject has one element") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1"))
                    .doesNotContain("item1")
            }.hasMessage(
                """
                    |▼ Expect that ["item1"]:
                    |   ✗ does not contain ["item1"]
                """.trimMargin(),
            )
        }

        test(name = "Containing the only checked element when the subject has multiple elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .doesNotContain("item2")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ does not contain ["item2"]
                """.trimMargin(),
            )
        }

        test(name = "Containing one of the checked elements when the subject has multiple elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .doesNotContain("item3", "item1")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ does not contain any of the elements ["item3", "item1"]:
                    |      ✓ does not contain "item3"
                    |
                    |      ✗ does not contain "item1"
                """.trimMargin(),
            )
        }
    }
}
