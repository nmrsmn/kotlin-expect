package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

val AnyFailureFormattingTest by testSuite(
    displayName = "Any failure formatting tests",
) {
    test(name = "`isA` assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat("value")
                .isA<Number>()
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is an instance of Number")
                .contains("but was: String")
        }
    }

    test(name = "`isNotA` assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat("value")
                .isNotA<String>()
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is not an instance of String")
        }
    }

    test(name = "`isNull` assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat("value" as String?)
                .isNull()
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is null")
        }
    }

    test(name = "`isNotNull` assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(null as String?)
                .isNotNull()
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is not null")
        }
    }
}
