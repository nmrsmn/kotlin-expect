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

    testSuite(name = "`containsExactly` failure formatting") {
        test(name = "Subject contains the expected elements but in a different order") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .containsExactly("item2", "item1")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ contains exactly the elements ["item2", "item1"]:
                    |      ✗ …at index 0:
                    |         ✗ contains "item2"
                    |           but was: "item1"
                    |
                    |      ✗ …at index 1:
                    |         ✗ contains "item1"
                    |           but was: "item2"
                    |
                    |      ✓ contains no further elements []
                """.trimMargin(),
            )
        }

        test(name = "Subject contains the expected elements but has extra elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2", "item3"))
                    .containsExactly("item1", "item2")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2", "item3"]:
                    |   ✗ contains exactly the elements ["item1", "item2"]:
                    |      ✓ …at index 0:
                    |         ✓ contains "item1"
                    |
                    |      ✓ …at index 1:
                    |         ✓ contains "item2"
                    |
                    |      ✗ contains no further elements []
                    |        but was: ["item3"]
                """.trimMargin(),
            )
        }

        test(name = "Subject does not contain all the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .containsExactly("item1", "item2", "item3")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ contains exactly the elements ["item1", "item2", "item3"]:
                    |      ✓ …at index 0:
                    |         ✓ contains "item1"
                    |
                    |      ✓ …at index 1:
                    |         ✓ contains "item2"
                    |
                    |      ✗ …at index 2:
                    |         ✗ contains "item3"
                    |
                    |      ✓ contains no further elements []
                """.trimMargin(),
            )
        }
    }

    testSuite(name = "`containsExactlyInAnyOrder` failure formatting") {
        test(name = "Subject contains the expected elements but has extra elements") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2", "item3"))
                    .containsExactlyInAnyOrder("item1", "item2")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2", "item3"]:
                    |   ✗ contains exactly the elements ["item1", "item2"] in any order:
                    |      ✓ contains "item1"
                    |
                    |      ✓ contains "item2"
                    |
                    |      ✗ contains no further elements []
                    |        but was: ["item3"]
                """.trimMargin(),
            )
        }

        test(name = "Subject does not contain all the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2"))
                    .containsExactlyInAnyOrder("item1", "item2", "item3")
            }.hasMessage(
                """
                    |▼ Expect that ["item1", "item2"]:
                    |   ✗ contains exactly the elements ["item1", "item2", "item3"] in any order:
                    |      ✓ contains "item1"
                    |
                    |      ✓ contains "item2"
                    |
                    |      ✗ contains "item3"
                    |
                    |      ✓ contains no further elements []
                """.trimMargin(),
            )
        }
    }
}
