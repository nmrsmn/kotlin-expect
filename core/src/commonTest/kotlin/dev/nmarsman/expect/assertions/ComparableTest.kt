package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.helper.Person
import kotlin.test.DefaultAsserter.fail

val ComparableAssertionTest by testSuite(
    displayName = "Comparable assertion tests",
) {
    testSuite(name = "`isGreaterThan` assertions") {
        test(name = "Passes if the subject is greater than the expected value") {
            expectThat(12)
                .isGreaterThan(10)
        }

        test(name = "Fails if the subject is not greater than the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat(12)
                    .isGreaterThan(15)
            }
        }
    }

    testSuite(name = "`isLessThan` assertions") {
        test(name = "Passes if the subject is less than the expected value") {
            expectThat(12)
                .isLessThan(15)
        }

        test(name = "Fails if the subject is not less than the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat(12)
                    .isLessThan(8)
            }
        }
    }

    testSuite(name = "`isGreaterThanOrEqualTo` assertions") {
        test(name = "Passes if the subject is greater than the expected value") {
            expectThat(12)
                .isGreaterThanOrEqualTo(10)
        }
        test(name = "Passes if the subject is equal to the expected value") {
            expectThat(12)
                .isGreaterThanOrEqualTo(12)
        }

        test(name = "Fails if the subject is not greater than or equal to the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat(12)
                    .isGreaterThanOrEqualTo(15)
            }
        }
    }

    testSuite(name = "`isLessThanOrEqualTo` assertions") {
        test(name = "Passes if the subject is less than the expected value") {
            expectThat(12)
                .isLessThanOrEqualTo(15)
        }
        test(name = "Passes if the subject is equal to the expected value") {
            expectThat(12)
                .isGreaterThanOrEqualTo(12)
        }

        test(name = "Fails if the subject is not less than or equal to the expected value") {
            expectThrows<AssertionFailedException> {
                expectThat(12)
                    .isLessThanOrEqualTo(10)
            }
        }
    }

    testSuite(name = "`isIn` assertions") {
        test("Passes if the subject is in the expected range") {
            expectThat(10)
                .isIn(1..10)
        }

        test("Fails if the subject is not in the expected range") {
            expectThrows<AssertionFailedException> {
                expectThat(12)
                    .isIn(1..10)
            }
        }
    }
}
