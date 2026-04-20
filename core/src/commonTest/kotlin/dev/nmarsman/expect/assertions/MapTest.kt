package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val MapAssertionTest by testSuite(
    displayName = "Map assertion tests",
) {
    testSuite(name = "`hasSize` assertions") {
        test(name = "Passes if the subject has the expected size") {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .hasSize(2)
        }

        test(name = "Fails if the subject does not have the expected size") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item1", 2 to "item2"))
                    .hasSize(3)
            }
        }
    }

    testSuite(name = "`isEmpty` assertions") {
        test(name = "Passes if the subject is empty") {
            expectThat(emptyMap<Int, String>())
                .isEmpty()
        }

        test(name = "Fails if the subject is not empty") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item"))
                    .isEmpty()
            }
        }
    }

    testSuite(name = "`isNotEmpty` assertions") {
        test(name = "Passes if the subject is not empty") {
            expectThat(mapOf(1 to "item"))
                .isNotEmpty()
        }

        test(name = "Fails if the subject is empty") {
            expectThrows<AssertionFailedException> {
                expectThat(emptyMap<Int, String>())
                    .isNotEmpty()
            }
        }
    }

    testSuite(name = "`containsKey` assertions") {
        test(name = "Passes if the subject contains the specified key") {
            expectThat(mapOf(1 to "item"))
                .containsKey(1)
        }

        test(name = "Fails if the subject does not contain the specified key") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item"))
                    .containsKey(2)
            }
        }
    }

    testSuite(name = "`doesNotContainKey` assertions") {
        test(name = "Passes if the subject does not contain the specified key") {
            expectThat(mapOf(1 to "item"))
                .doesNotContainKey(2)
        }

        test(name = "Fails if the subject contains the specified key") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item"))
                    .doesNotContainKey(1)
            }
        }
    }

    testSuite(name = "`containsKeys` assertions") {
        test(name = "Passes if the subject contains all specified keys") {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .containsKeys(1, 2)
        }

        test(name = "Fails if the subject does not contain all specified keys") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item1", 2 to "item2"))
                    .containsKeys(1, 3)
            }
        }
    }

    testSuite(name = "doesNotContainKeys` assertions") {
        test(name = "Passes if the subject contains none of the specified keys") {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .doesNotContainKeys(3, 4)
        }

        test(name = "Fails if the subject contains one of the specified keys") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item1", 2 to "item2"))
                    .doesNotContainKeys(1, 4)
            }
        }

        test(name = "Fails if the subject contains all specified keys") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item1", 2 to "item2"))
                    .doesNotContainKeys(1, 3)
            }
        }
    }

    testSuite(name = "`hasEntry` assertions") {
        test(name = "Passes if the subject contains the specified entry") {
            expectThat(mapOf(1 to "item1", 2 to "item2"))
                .hasEntry(1 to "item1")
        }

        test(name = "Fails if the subject does not contain the specified entry") {
            expectThrows<AssertionFailedException> {
                expectThat(mapOf(1 to "item1", 2 to "item2"))
                    .hasEntry(1, "item2")
            }
        }
    }
}
