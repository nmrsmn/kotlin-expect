package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.exception.AssertionFailedException
import kotlin.test.DefaultAsserter.fail

private data class Person(
    val name: String,
    val age: Int,
)

val AnyEqualityTest by testSuite(displayName = "Equality tests") {
    testSuite(name = "Null equality") {
        test(name = "Null equality check should pass if the subject is null and the expected value is null") {
            expectThat(subject = null)
                .isEqualTo(expected = null)
        }
    }

    testSuite(name = "Data class equality") {
        test(name = "Data class equality check should pass if the subject is equal to the expected value") {
            val person = Person(name = "John", age = 23)

            expectThat(subject = person)
                .isEqualTo(expected = Person(name = "John", age = 23))
        }

        test(name = "Data class equality check should fail if the subject is not equal to the expected value") {
            val person = Person(name = "John", age = 23)

            try {
                expectThat(subject = person)
                    .isEqualTo(expected = Person(name = "Doe", age = 23))

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }

        test(name = "Data class equality check should fail if the expected value is null") {
            val person = Person(name = "John", age = 23)

            try {
                expectThat(subject = person)
                    .isEqualTo(expected = null)

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }

    testSuite(name = "String equality") {
        test(name = "String equality check should pass if the subject is equal to the expected value") {
            expectThat(subject = "String")
                .isEqualTo(expected = "String")
        }

        test(name = "String equality check should fail if the subject is not equal to the expected value") {
            try {
                expectThat(subject = "String")
                    .isEqualTo(expected = "Some other value")

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }

        test(name = "String equality check should fail if the expected value is null") {
            try {
                expectThat(subject = "String")
                    .isEqualTo(expected = null)

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }

    testSuite(name = "Boolean equality") {
        test(name = "Boolean equality check should pass if the subject is equal to the expected value - true") {
            expectThat(subject = true)
                .isEqualTo(expected = true)
        }

        test(name = "Boolean equality check should fail if the subject is not equal to the expected value") {
            try {
                expectThat(subject = true)
                    .isEqualTo(expected = false)

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }

        test(name = "Boolean equality check should fail if the expected value is null") {
            try {
                expectThat(subject = false)
                    .isEqualTo(expected = null)

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }

    testSuite(name = "Number equality") {
        testSuite(name = "Integer equality") {
            test("Integer equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = 42)
                    .isEqualTo(expected = 42)
            }

            test(name = "Integer equality check should fail if the subject is not equal to the expected value") {
                try {
                    expectThat(subject = 42)
                        .isEqualTo(expected = 2)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "Integer equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = 42)
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }
        }

        testSuite(name = "Long equality") {
            test(name = "Long equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = 42L)
                    .isEqualTo(expected = 42L)
            }

            test(name = "Long equality check should fail if the subject is not equal to the expected value") {
                try {
                    expectThat(subject = 42L)
                        .isEqualTo(expected = 2L)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "Long equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = 42L)
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }
        }

        testSuite(name = "Double equality") {
            test(name = "Double equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = 42.0)
                    .isEqualTo(expected = 42.0)
            }

            test(name = "Double equality check should fail if the subject is not equal to the expected value") {
                try {
                    expectThat(subject = 42.0)
                        .isEqualTo(expected = 2.0)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "Double equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = 42.0)
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }
        }

        testSuite(name = "Float equality") {
            test(name = "Float equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = 42f)
                    .isEqualTo(expected = 42f)
            }

            test(name = "Float equality check should fail if the subject is not equal to the expected value") {
                try {
                    expectThat(subject = 42f)
                        .isEqualTo(expected = 2f)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "Float equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = 42f)
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }
        }
    }

    testSuite(name = "Collection equality") {
        testSuite(name = "List equality") {
            test(name = "List equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = listOf(1, 2, 3))
                    .isEqualTo(expected = listOf(1, 2, 3))
            }

            test(name = "List equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = listOf(1, 2, 3))
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            testSuite(name = "List equality check should fail if the subject is not equal to the expected value") {
                test(name = "Same list length") {
                    try {
                        expectThat(subject = listOf(1, 2, 3))
                            .isEqualTo(expected = listOf(1, 2, 4))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }

                test(name = "Different list length") {
                    try {
                        expectThat(subject = listOf(1, 2, 3))
                            .isEqualTo(expected = listOf(1, 2, 3, 4))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }
            }
        }

        testSuite(name = "Set equality") {
            test(name = "Set equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = setOf(1, 2, 3))
                    .isEqualTo(expected = setOf(1, 2, 3))
            }

            test(name = "Set equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = setOf(1, 2, 3))
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            testSuite(name = "Set equality check should fail if the subject is not equal to the expected value") {
                test(name = "Same set length") {
                    try {
                        expectThat(subject = setOf(1, 2, 3))
                            .isEqualTo(expected = setOf(1, 2, 4))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }

                test(name = "Different set length") {
                    try {
                        expectThat(subject = setOf(1, 2, 3))
                            .isEqualTo(expected = setOf(1, 2, 3, 4))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }
            }
        }

        testSuite(name = "Map equality") {
            test(name = "Map equality check should pass if the subject is equal to the expected value") {
                expectThat(subject = mapOf(1 to "one", 2 to "two", 3 to "three"))
                    .isEqualTo(expected = mapOf(1 to "one", 2 to "two", 3 to "three"))
            }

            test(name = "Map equality check should fail if the expected value is null") {
                try {
                    expectThat(subject = mapOf(1 to "one", 2 to "two", 3 to "three"))
                        .isEqualTo(expected = null)

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            testSuite(name = "Map equality check should fail if the subject is not equal to the expected value") {
                test(name = "Same map size") {
                    try {
                        expectThat(subject = mapOf(1 to "one", 2 to "two", 3 to "three"))
                            .isEqualTo(expected = mapOf(1 to "one", 2 to "two", 4 to "four"))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }

                test(name = "Different map size") {
                    try {
                        expectThat(subject = mapOf(1 to "one", 2 to "two", 3 to "three"))
                            .isEqualTo(expected = mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four"))

                        fail("Test should have thrown")
                    } catch (_: AssertionFailedException) {
                        // Test should have thrown
                    }
                }
            }
        }

        testSuite(name = "Array equality") {
            test(name = "Array equality check should fail by default (not supported yet)") {
                try {
                    expectThat(subject = arrayOf(1, 2, 3))
                        .isEqualTo(expected = arrayOf(1, 2, 3))

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "ByteArray equality check should fail by default (not supported yet)") {
                try {
                    expectThat(subject = byteArrayOf(1, 2, 3))
                        .isEqualTo(expected = byteArrayOf(1, 2, 3))

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }

            test(name = "IntArray equality check should fail by default (not supported yet)") {
                try {
                    expectThat(subject = intArrayOf(1, 2, 3))
                        .isEqualTo(expected = intArrayOf(1, 2, 3))

                    fail("Test should have thrown")
                } catch (_: AssertionFailedException) {
                    // Test should have thrown
                }
            }
        }
    }

    testSuite(name = "Chaining equality") {
        test(name = "Chaining equality checks still passes when the subject is equal to the expected value") {
            expectThat(subject = 42)
                .isEqualTo(expected = 42)
                .isEqualTo(expected = 42)
        }

        test(name = "Chaining equality checks fail when the subject is not equal to the expected value") {
            try {
                expectThat(subject = 42)
                    .isEqualTo(expected = 42)
                    .isEqualTo(expected = 1)

                fail("Test should have thrown")
            } catch (_: AssertionFailedException) {
                // Test should have thrown
            }
        }
    }
}
