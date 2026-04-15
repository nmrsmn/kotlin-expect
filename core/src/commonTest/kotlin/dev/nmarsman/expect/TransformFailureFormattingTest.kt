package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.helper.Person

val TransformFailureFormattingTest by testSuite(
    displayName = "Transform failure formatting tests",
) {
    testSuite(name = "Transform using `get`") {
        test(name = "Should pass when assertion on transformed property fails") {
            expectThrows<AssertionError> {
                expectThat(Person(name = "John", age = 23))
                    .get(Person::name)
                    .isEqualTo("David")
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that Person(name=John, age=23):
                            |   ▼ value of the property name ("John"):
                            |      ✗ is equal to "David"
                        """.trimMargin(),
                    )
            }
        }

        test(name = "Should pass when assertion on transformed computed property fails") {
            expectThrows<AssertionError> {
                expectThat("Hello, world")
                    .get(String::length)
                    .isEqualTo(11)
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that "Hello, world":
                            |   ▼ value of the property length (12):
                            |      ✗ is equal to 11
                        """.trimMargin(),
                    )
            }
        }

        test(name = "Should pass when assertion on chained transformed property fails") {
            expectThrows<AssertionError> {
                expectThat(Person(name = "John", age = 23))
                    .get { name }
                    .get { length }
                    .isEqualTo(8)
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that Person(name=John, age=23):
                            |   ▼ <anonymous lambda> ("John"):
                            |      ▼ <anonymous lambda> (4):
                            |         ✗ is equal to 8
                        """.trimMargin(),
                    )
            }
        }
    }

    testSuite(name = "Transform using `with`") {
        test(name = "Should pass when assertion on transformed property fails") {
            expectThrows<AssertionError> {
                expectThat(Person(name = "John", age = 23))
                    .with(Person::name) {
                        isEqualTo("David")
                    }
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that Person(name=John, age=23):
                            |   ▼ value of the property name ("John"):
                            |      ✗ is equal to "David"
                        """.trimMargin(),
                    )
            }
        }

        test(name = "Should pass when assertion on transformed computed property fails") {
            expectThrows<AssertionError> {
                expectThat("Hello, world")
                    .with(String::length) {
                        isEqualTo(11)
                    }
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that "Hello, world":
                            |   ▼ value of the property length (12):
                            |      ✗ is equal to 11
                        """.trimMargin(),
                    )
            }
        }

        test(name = "Should pass when assertion on chained transformed property fails") {
            expectThrows<AssertionError> {
                expectThat(Person(name = "John", age = 23))
                    .with(Person::name) {
                        with(String::length) {
                            isEqualTo(8)
                        }
                    }
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that Person(name=John, age=23):
                            |   ▼ value of the property name ("John"):
                            |      ▼ value of the property length (4):
                            |         ✗ is equal to 8
                        """.trimMargin(),
                    )
            }
        }
    }
}
