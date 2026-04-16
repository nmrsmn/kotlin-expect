package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val CollectionAssertionTest by testSuite(
    displayName = "Collection assertion tests",
) {
    testSuite(name = "`hasSize` assertions") {
        test(name = "Passes if the subject has the expected size") {
            expectThat(listOf("item1", "item2"))
                .hasSize(2)
        }

        test(name = "Fails if the subject does not have the expected size") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .hasSize(3)
            }
        }
    }

    testSuite(name = "`isEmpty` assertions") {
        test(name = "Passes if the subject is empty") {
            expectThat(emptyList<String>())
                .isEmpty()
        }

        test(name = "Fails if the subject is not empty") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item"))
                    .isEmpty()
            }
        }
    }

    testSuite(name = "`isNotEmpty` assertions") {
        test(name = "Passes if the subject is not empty") {
            expectThat(listOf("item"))
                .isNotEmpty()
        }

        test(name = "Fails if the subject is empty") {
            expectThrows<AssertionFailedException> {
                expectThat(emptyList<String>())
                    .isNotEmpty()
            }
        }
    }
}
