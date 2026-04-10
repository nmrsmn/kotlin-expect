package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person
import kotlin.test.DefaultAsserter.fail

val AnyAssertionTest by testSuite(
    displayName = "Any assertion tests",
) {
    testSuite(name = "`isA` assertions") {
        test(name = "Passes if the subject is an instance of the expected type") {
            expectThat("value")
                .isA<String>()
        }

        test(name = "Passes if the subject is an instance of a subtype of the expected type") {
            expectThat(1L)
                .isA<Number>()
        }

        test(name = "Fails if the subject is not an instance of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat("value")
                    .isA<Number>()
            }
        }

        test(name = "Fails if the subject is `null`") {
            expectThrows<AssertionFailedException> {
                expectThat(null)
                    .isA<String>()
            }
        }
    }

    testSuite(name = "`isNotA` assertions") {
        test(name = "Passes if the subject is not an instance of the expected type") {
            expectThat("value")
                .isNotA<Number>()
        }

        test(name = "Passes if the subject is `null`") {
            expectThat(null)
                .isNotA<String>()
        }

        test(name = "Fails if the subject is an instance of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat("value")
                    .isNotA<String>()
            }
        }

        test(name = "Fails if the subject is an instance of a subtype of the expected type") {
            expectThrows<AssertionFailedException> {
                expectThat(1L)
                    .isNotA<Number>()
            }
        }
    }

    testSuite(name = "`isEqualTo` assertions") {
        test(name = "Passes if the subject is equal to the expected value") {
            expectThat("subject")
                .isEqualTo("subject")
        }

        test(name = "Fails if the subject is not equal to the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat("subject")
                    .isEqualTo("value")
            }
        }

        test(name = "Fails if the expected value is `null`") {
            expectThrows<AssertionFailedException> {
                expectThat("subject")
                    .isEqualTo(null)
            }
        }
    }

    testSuite(name = "`isNotEqualTo` assertions") {
        test(name = "Passes if the subject is not equal to the expected value") {
            expectThat("subject")
                .isNotEqualTo("value")
        }

        test(name = "Passes if the expected value is `null`") {
            expectThat("subject")
                .isNotEqualTo(null)
        }

        test(name = "Fails if the subject is equal to the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat("subject")
                    .isNotEqualTo("subject")
            }
        }
    }

    testSuite(name = "`isNull` assertions") {
        test("isNull should pass if the subject is null") {
            expectThat(null)
                .isNull()
        }

        test("isNull should fail if the subject is not null") {
            expectThrows<AssertionFailedException> {
                expectThat("value" as String?)
                    .isNull()
            }
        }
    }

    testSuite(name = "`isNotNull` assertions") {
        test("Passes if the subject is not null") {
            expectThat("subject" as String?)
                .isNotNull()
        }

        test("Fails if the subject is null") {
            expectThrows<AssertionFailedException> {
                expectThat(null)
                    .isNotNull()
            }
        }
    }

    testSuite(name = "`isSameInstanceAs` assertions") {
        test("Passes if the subject is the same instance as the expected value") {
            val subject = "subject"

            expectThat(subject)
                .isSameInstanceAs(subject)
        }

        test("Fails if the subject is not the same instance as the expected value - value equal") {
            val subject = Person(name = "John", age = 23)
            val expected = Person(name = "John", age = 23)

            expectThrows<AssertionFailedException> {
                expectThat(subject)
                    .isSameInstanceAs(expected)
            }
        }

        test("Fails if the subject is not the same instance as the expected value - value not equal") {
            expectThrows<AssertionFailedException> {
                expectThat("value")
                    .isSameInstanceAs("other")
            }
        }
    }

    testSuite(name = "`isNotSameInstanceAs` assertions") {
        test("Passes if the subject is not the same instance as the expected value - value equal") {
            val subject = Person(name = "John", age = 23)
            val expected = Person(name = "John", age = 23)

            expectThat(subject)
                .isNotSameInstanceAs(expected)
        }

        test("Passes if the subject is not the same instance as the expected value - value not equal") {
            expectThat("value")
                .isNotSameInstanceAs("other")
        }

        test("Fails if the subject is the same instance as the expected value") {
            expectThrows<AssertionFailedException> {
                val subject = "subject"

                expectThat(subject)
                    .isNotSameInstanceAs(subject)
            }
        }
    }
}
