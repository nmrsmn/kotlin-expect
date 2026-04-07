package dev.nmarsman.expect.internal

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.helper.Person

val FunctionDescriptorTest by testSuite(displayName = "Function descriptor tests") {
    testSuite(name = "Receiver function describe") {
        test("KProperty reference describes as 'value of the property'") {
            val function: Person.() -> String = Person::name

            expectThat(function.describe())
                .isEqualTo("value of the property name")
        }

        test("KFunction reference describes as 'return value of'") {
            val function: Person.() -> String = Person::toString

            expectThat(function.describe())
                .isEqualTo("return value of toString")
        }

        test("Anonymous lambda describes as '<anonymous>'") {
            val function: Person.() -> String = { name }

            expectThat(function.describe())
                .isEqualTo("<anonymous>")
        }
    }
}
