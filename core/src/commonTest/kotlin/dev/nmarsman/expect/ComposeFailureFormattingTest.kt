package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.hasMessage
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person

val ComposeFailureFormattingTest by testSuite(
    displayName = "Composed assertion failure formatting tests",
) {
    test(name = "`compose` assertion failure is formatted correctly on a simple node") {
        expectThrows<AssertionFailedException> {
            expectThat(150)
                .compose("is a valid number") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100") { it < 100 }
                } require { all }
        }.also {
            it.hasMessage(
                """
                    |▼ Expect that 150:
                    |   ✗ is a valid number:
                    |      ✓ is positive
                    |
                    |      ✗ is less than 100
                """.trimMargin(),
            )
        }
    }

    test(name = "`compose` assertion failure is formatted correctly on a expressive node") {
        expectThrows<AssertionFailedException> {
            expectThat(Person(name = "John", age = 10))
                .compose("is a valid number") {
                    get(Person::name)
                        .isEqualTo("John")

                    get(Person::age)
                        .isEqualTo(100)
                } require { all }
        }.also {
            it.hasMessage(
                """
                    |▼ Expect that Person(name=John, age=10):
                    |   ✗ is a valid number:
                    |      ▼ value of the property name ("John"):
                    |         ✓ is equal to "John"
                    |
                    |      ▼ value of the property age (10):
                    |         ✗ is equal to 100
                """.trimMargin(),
            )
        }
    }
}
