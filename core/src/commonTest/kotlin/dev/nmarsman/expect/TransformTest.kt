package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.assertions.isNotBlank
import dev.nmarsman.expect.assertions.isNotEmpty
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person
import dev.nmarsman.expect.internal.AssertionBuilder

val TransformTests by testSuite(
    displayName = "Transform tests",
) {
    testSuite(name = "Transform using `get`") {
        test(name = "Transforming on a property should pass when comparing the transformed value") {
            expectThat(Person(name = "John", age = 23))
                .get { name }
                .isEqualTo("John")
        }

        test(name = "Transforming on a computed value should pass when comparing the transformed value") {
            expectThat("Hello, world")
                .get { length }
                .isEqualTo(12)
        }

        test(name = "Transforming using a referenced property should pass when comparing the transformed value") {
            expectThat(Person(name = "John", age = 23))
                .get(Person::name)
                .isEqualTo("John")
        }

        test(name = "Transforming using a referenced computed value should pass when comparing the transformed value") {
            expectThat("Hello, world")
                .get(String::length)
                .isEqualTo(12)
        }

        test(name = "Chaining transformations should pass when comparing the transformed value") {
            expectThat(Person(name = "John", age = 23))
                .get { name }
                .get { length }
                .isEqualTo(4)
        }
    }

    testSuite(name = "Scoped transform using `with`") {
        test(name = "Transforming on a property should pass when comparing the transformed value") {
            expectThat(Person(name = "John", age = 23))
                .with(Person::name) {
                    isEqualTo("John")
                }
        }

        test(name = "Transforming on a computed value should pass when comparing the transformed value") {
            expectThat("Hello, world")
                .with(String::length) {
                    isEqualTo(12)
                }
        }

        test(name = "Chaining transformations should pass when comparing the transformed value") {
            expectThat(Person(name = "John", age = 23))
                .with(Person::name) {
                    with(String::length) {
                        isEqualTo(4)
                    }
                }
        }

        test(name = "Transforming on a property should fail when comparing the transformed value") {
            expectThrows<AssertionFailedException> {
                expectThat(Person(name = "John", age = 23))
                    .with(Person::name) {
                        isEqualTo("Eric")
                    }
            }
        }

        test(name = "Chaining transformations should fail when comparing the transformed value") {
            expectThrows<AssertionFailedException> {
                expectThat(Person(name = "John", age = 23))
                    .with(Person::name) {
                        isNotBlank()
                        isNotEmpty()
                        isEqualTo("Eric")
                    }
            }
        }

        test(name = "Simple transformations should pass when reference assertion is used") {
            expectThat(Person(name = "John", age = 23))
                .with(Person::name, Assertion.Builder<CharSequence>::isNotBlank)
        }
    }
}
