package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isA
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.assertions.isNull
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person

val FormattingTest by testSuite {
    test(name = "A failing chained assertion formats the message correctly") {
        try {
            expectThat(42)
                .isA<Number>()
                .isA<Long>()
        } catch (exception: AssertionFailedException) {
            val expected = """
                |▼ Expect that 42:
                |   ✓ is an instance of Number
                |
                |   ✗ is an instance of Long
                |     but was: Int
            """.trimMargin()

            expectThat(subject = exception.message)
                .isEqualTo(expected = expected)
        }
    }

    test(name = "A failing 'is a' assertion formats the message correctly") {
        try {
            expectThat(42)
                .isA<String>()
        } catch (exception: AssertionFailedException) {
            val expected = """
                |▼ Expect that 42:
                |   ✗ is an instance of String
                |     but was: Int
            """.trimMargin()

            expectThat(subject = exception.message)
                .isEqualTo(expected = expected)
        }
    }

    testSuite(name = "A failing 'is equal to' assertion formats the message correctly") {
        test(name = "Data class equality") {
            try {
                val person = Person(name = "John", age = 23)
                expectThat(person)
                    .isEqualTo(Person(name = "Doe", age = 24))
            } catch (exception: AssertionFailedException) {
                val expected = """
                    |▼ Expect that Person(name=John, age=23):
                    |   ✗ is equal to Person(name=Doe, age=24)
                """.trimMargin()

                expectThat(subject = exception.message)
                    .isEqualTo(expected = expected)
            }
        }

        test(name = "String equality") {
            try {
                expectThat("Hello, World!")
                    .isEqualTo("Some other value")
            } catch (exception: AssertionFailedException) {
                val expected = """
                    |▼ Expect that "Hello, World!":
                    |   ✗ is equal to "Some other value"
                """.trimMargin()

                expectThat(subject = exception.message)
                    .isEqualTo(expected = expected)
            }
        }

        test(name = "Char equality") {
            try {
                expectThat('a')
                    .isEqualTo('b')
            } catch (exception: AssertionFailedException) {
                val expected = """
                    |▼ Expect that 'a':
                    |   ✗ is equal to 'b'
                """.trimMargin()

                expectThat(subject = exception.message)
                    .isEqualTo(expected = expected)
            }
        }

        test(name = "Regex equality") {
            try {
                expectThat("^.*$".toRegex())
                    .isEqualTo("^$".toRegex())
            } catch (exception: AssertionFailedException) {
                val expected = """
                    |▼ Expect that /^.*$/:
                    |   ✗ is equal to /^$/
                """.trimMargin()

                expectThat(subject = exception.message)
                    .isEqualTo(expected = expected)
            }
        }

        test(name = "Boolean equality") {
            try {
                expectThat(true)
                    .isEqualTo(false)
            } catch (exception: AssertionFailedException) {
                val expected = """
                    |▼ Expect that true:
                    |   ✗ is equal to false
                """.trimMargin()

                expectThat(subject = exception.message)
                    .isEqualTo(expected = expected)
            }
        }

        testSuite(name = "Number equality") {
            test(name = "Byte equality") {
                try {
                    expectThat(0x42.toByte())
                        .isEqualTo(0x1.toByte())
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that 0x42:
                        |   ✗ is equal to 0x1
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            test(name = "Int equality") {
                try {
                    expectThat(42)
                        .isEqualTo(1)
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that 42:
                        |   ✗ is equal to 1
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            test(name = "Long equality") {
                try {
                    expectThat(42L)
                        .isEqualTo(1L)
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that 42:
                        |   ✗ is equal to 1
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            test(name = "Double equality") {
                try {
                    expectThat(42.0)
                        .isEqualTo(1.0)
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that 42.0:
                        |   ✗ is equal to 1.0
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            test(name = "Float equality") {
                try {
                    expectThat(42f)
                        .isEqualTo(1f)
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that 42.0:
                        |   ✗ is equal to 1.0
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }
        }

        testSuite(name = "Collection equality") {
            test(name = "Pair equality") {
                try {
                    expectThat(1 to "one")
                        .isEqualTo(2 to "two")
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that (1, "one"):
                        |   ✗ is equal to (2, "two")
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            testSuite(name = "List equality") {
                test(name = "List of Ints") {
                    try {
                        expectThat(listOf(1, 2, 3))
                            .isEqualTo(listOf(1, 2, 4))
                    } catch (exception: AssertionFailedException) {
                        val expected = """
                            |▼ Expect that [1, 2, 3]:
                            |   ✗ is equal to [1, 2, 4]
                        """.trimMargin()

                        expectThat(subject = exception.message)
                            .isEqualTo(expected = expected)
                    }
                }

                test(name = "List of Strings") {
                    try {
                        expectThat(listOf("1", "2", "3"))
                            .isEqualTo(listOf("1", "2", "4"))
                    } catch (exception: AssertionFailedException) {
                        val expected = """
                            |▼ Expect that ["1", "2", "3"]:
                            |   ✗ is equal to ["1", "2", "4"]
                        """.trimMargin()

                        expectThat(subject = exception.message)
                            .isEqualTo(expected = expected)
                    }
                }
            }

            test(name = "Set equality") {
                try {
                    expectThat(setOf(1, 2, 3))
                        .isEqualTo(setOf(1, 2, 4))
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that [1, 2, 3]:
                        |   ✗ is equal to [1, 2, 4]
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            test(name = "Map equality") {
                try {
                    expectThat(mapOf(1 to "one", 2 to "two", 3 to "three"))
                        .isEqualTo(mapOf(1 to "one", 2 to "two", 4 to "four"))
                } catch (exception: AssertionFailedException) {
                    val expected = """
                        |▼ Expect that {1="one", 2="two", 3="three"}:
                        |   ✗ is equal to {1="one", 2="two", 4="four"}
                    """.trimMargin()

                    expectThat(subject = exception.message)
                        .isEqualTo(expected = expected)
                }
            }

            testSuite(name = "ByteArray equality") {
                test(name = "Empty ByteArray") {
                    val subject = byteArrayOf()

                    try {
                        expectThat(subject = subject)
                            .isEqualTo(byteArrayOf(1))
                    } catch (exception: AssertionFailedException) {
                        val expected = """
                            |▼ Expect that 0x:
                            |   ✗ is equal to 0x01
                        """.trimMargin()

                        expectThat(subject = exception.message)
                            .isEqualTo(expected = expected)
                    }
                }

                test(name = "Short ByteArray") {
                    val subject = ByteArray(8) {
                        255.toByte()
                    }

                    try {
                        expectThat(subject = subject)
                            .isEqualTo(byteArrayOf(1))
                    } catch (exception: AssertionFailedException) {
                        val expected = """
                            |▼ Expect that 0xffffffffffffffff:
                            |   ✗ is equal to 0x01
                        """.trimMargin()

                        expectThat(subject = exception.message)
                            .isEqualTo(expected = expected)
                    }
                }

                test(name = "Long ByteArray") {
                    val subject = byteArrayOf(
                        1, 23, 127, 127, 127, 1, 3, 127, 84, 123, 122,
                        23, 127, 127, 127, 1, 3, 127, 84, 123, 122,
                        23, 127, 127, 127, 1, 3, 127, 84, 123, 122,
                        23, 127, 127, 127, 1, 3, 127, 84, 123, 122,
                        23, 127, 127, 127, 1, 3, 127, 84, 123, 122,
                    )

                    try {
                        expectThat(subject = subject)
                            .isEqualTo(byteArrayOf(1))
                    } catch (exception: AssertionFailedException) {
                        val expected = """
                            |▼ Expect that 0x01177f7f7f01037f547b7a177f7f7f01037f54…:
                            |   ✗ is equal to 0x01
                        """.trimMargin()

                        expectThat(subject = exception.message)
                            .isEqualTo(expected = expected)
                    }
                }
            }
        }
    }

    test(name = "A failing 'is null' assertion formats the message correctly") {
        try {
            expectThat(42 as Int?)
                .isNull()
        } catch (exception: AssertionFailedException) {
            val expected = """
                |▼ Expect that 42:
                |   ✗ is null
            """.trimMargin()

            expectThat(subject = exception.message)
                .isEqualTo(expected = expected)
        }
    }
}
