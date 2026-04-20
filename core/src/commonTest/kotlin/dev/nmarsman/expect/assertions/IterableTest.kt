package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

private fun Assertion.Builder<Int>.isEven() =
    assertThat("is even") { subject % 2 == 0 }

val IterableAssertionTest by testSuite(
    displayName = "Iterable assertion tests",
) {
    testSuite(name = "`all` predicate assertions") {
        test(name = "Passes if all elements match the predicate") {
            expectThat(listOf(2, 4, 6))
                .all {
                    isEven()
                }
        }

        test(name = "Passes if the subject is empty") {
            expectThat(emptyList<Int>())
                .all {
                    isEven()
                }
        }

        test(name = "Fails if one element does not match the predicate") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(2, 3, 4))
                    .all {
                        isEven()
                    }
            }
        }

        test(name = "Fails if no elements match the predicate") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(1, 3, 5))
                    .all {
                        isEven()
                    }
            }
        }
    }

    testSuite(name = "`any` predicate assertions") {
        test(name = "Passes if at least one element matches the predicate") {
            expectThat(listOf(1, 2, 3))
                .any {
                    isEven()
                }
        }

        test(name = "Passes if all elements match the predicate") {
            expectThat(listOf(2, 4, 6))
                .any {
                    isEven()
                }
        }

        test(name = "Fails if the subject is empty") {
            expectThrows<AssertionFailedException> {
                expectThat(emptyList<Int>())
                    .any {
                        isEven()
                    }
            }
        }

        test(name = "Fails if no elements match the predicate") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(1, 3, 5))
                    .any {
                        isEven()
                    }
            }
        }
    }

    testSuite(name = "`none` predicate assertions") {
        test(name = "Passes if no elements match the predicate") {
            expectThat(listOf(1, 3, 5))
                .none {
                    isEven()
                }
        }

        test(name = "Passes if the subject is empty") {
            expectThat(emptyList<Int>())
                .none {
                    isEven()
                }
        }

        test(name = "Fails if one element matches the predicate") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(1, 2, 3))
                    .none {
                        isEven()
                    }
            }
        }

        test(name = "Fails if all elements match the predicate") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(2, 4, 6))
                    .none {
                        isEven()
                    }
            }
        }
    }

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

    testSuite(name = "`isSorted` assertions") {
        test(name = "Passes if the subject is empty") {
            expectThat(emptyList<Int>())
                .isSorted()
        }

        test(name = "Passes if the subject has a single element") {
            expectThat(listOf(1))
                .isSorted()
        }

        test(name = "Passes if the subject is sorted in ascending order") {
            expectThat(listOf(1, 2, 3, 4, 5))
                .isSorted()
        }

        test(name = "Passes if the subject is sorted in descending order") {
            expectThat(listOf(5, 4, 3, 2, 1))
                .isSorted(compareByDescending { it })
        }

        test(name = "Passes if the subject contains equal adjacent elements") {
            expectThat(listOf(1, 1, 2, 2, 3))
                .isSorted()
        }

        test(name = "Fails if the subject is not sorted") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(1, 3, 2, 4))
                    .isSorted()
            }
        }

        test(name = "Fails if the subject is sorted in the opposite order") {
            expectThrows<AssertionFailedException> {
                expectThat(listOf(5, 4, 3, 2, 1))
                    .isSorted()
            }
        }
    }
}
