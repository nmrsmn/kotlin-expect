package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.helper.Person

val FormattingTransformedNodeTest by testSuite(
    displayName = "Formatting transformed node tests",
) {
    test("Formatting when transforming on a referenced property should pass when that property is in the output") {
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

    test("Formatting when transforming on a referenced computed value should pass when that value is in the output") {
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

    test(name = "Formatting when transforming on multiple properties should pass when that property is in the output") {
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
