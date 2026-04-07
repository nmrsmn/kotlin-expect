package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.helper.Person

val TransformTests by testSuite {
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
