package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person
import kotlin.test.DefaultAsserter.fail

val NumberFailureFormattingTest by testSuite(
    name = "Number failure formatting tests",
) {
    test(name = "`isEqual(Double)` with tolerance assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(subject = 5.001)
                .isEqualTo(expected = 5.0, tolerance = 0.0001)
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is within 1.0E-4 of 5.0")
                .contains("differs by 0.001")
        }
    }

    test(name = "`isEqual(Float)` with tolerance assertion failure is formatted correctly") {
        expectThrows<AssertionFailedException> {
            expectThat(subject = 5.001)
                .isEqualTo(expected = 5.0, tolerance = 0.0001)
        }.also {
            it.get(AssertionFailedException::message)
                .isNotNull()
                .contains("✗ is within 1.0E-4 of 5.0")
                .contains("differs by 0.001")
        }
    }
}
