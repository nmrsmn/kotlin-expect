package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val IterableAssertionTest by testSuite(
    displayName = "Iterable assertion tests",
) {
    testSuite(name = "`contains` assertions") {
        test(name = "Passes if the expected elements are empty") {
            expectThat(listOf("item1", "item2"))
                .contains()
        }

        test(name = "Passes if the subject contains the expected element") {
            expectThat(listOf("item1", "item2"))
                .contains("item1")
        }

        test(name = "Passes if the subject contains multiple expected elements") {
            expectThat(listOf("item1", "item2", "item3"))
                .contains("item1", "item2")
        }

        test(name = "Fails if the subject does not contain the expected element") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .contains("item3")
            }
        }

        test(name = "Fails if the subject does not contain one of the the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .contains("item3", "item1")
            }
        }
    }

    testSuite(name = "`doesNotContain` assertions") {
        test(name = "Passes if the subject does not contain the expected element") {
            expectThat(listOf("item1", "item2"))
                .doesNotContain("item3")
        }

        test(name = "Passes if the subject does not contain multiple expected elements") {
            expectThat(listOf("item1", "item2", "item3"))
                .doesNotContain("item4", "item5")
        }

        test(name = "Fails if the the expected elements are empty") {
            expectThrows<IllegalArgumentException> {
                expectThat(listOf("item1", "item2"))
                    .doesNotContain()
            }.hasMessage("You must supply some expected elements.")
        }

        test(name = "Fails if the subject contains the expected element") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .doesNotContain("item1")
            }
        }

        test(name = "Fails if the subject contains one of the the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .doesNotContain("item3", "item1")
            }
        }
    }

    testSuite(name = "`containsExactly` assertions") {
        test(name = "Fails if the expected elements are empty") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .containsExactly()
            }
        }

        test(name = "Passes if the subject contains all the expected element - in order") {
            expectThat(listOf("item1", "item2"))
                .containsExactly("item1", "item2")
        }

        test(name = "Fails if the subject contains all the expected element - not in order") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .containsExactly("item2", "item1")
            }
        }

        test(name = "Fails if the subject contains more elements than the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2", "item3"))
                    .containsExactly("item1", "item2")
            }
        }

        test(name = "Fails if the subject contains less elements than the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1"))
                    .containsExactly("item2", "item1")
            }
        }

        test(name = "Fails if the subject does not contain all of the the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf("item1", "item2"))
                    .containsExactly("item3", "item1")
            }
        }
    }

    testSuite(name = "`containsExactlyInAnyOrder` assertions") {
        test(name = "Fails if the expected elements are empty") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2"))
                    .containsExactlyInAnyOrder()
            }
        }

        test(name = "Passes if the subject contains all the expected element - in order") {
            expectThat(setOf("item1", "item2"))
                .containsExactlyInAnyOrder("item1", "item2")
        }

        test(name = "Passes if the subject contains all the expected element - not in order") {
            expectThat(setOf("item1", "item2"))
                .containsExactlyInAnyOrder("item2", "item1")
        }

        test(name = "Fails if the subject contains more elements than the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2", "item3"))
                    .containsExactlyInAnyOrder("item1", "item2")
            }
        }

        test(name = "Fails if the subject contains less elements than the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2"))
                    .containsExactlyInAnyOrder("item1", "item2", "item3")
            }
        }

        test(name = "Fails if the subject does not contain all of the the expected elements") {
            expectThrows<AssertionFailedException> {
                expectThat(setOf("item1", "item2"))
                    .containsExactlyInAnyOrder("item3", "item1")
            }
        }
    }
}
