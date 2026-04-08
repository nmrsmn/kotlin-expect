package dev.nmarsman.expect.internal

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isA
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.assertions.isNull

val AssertionResultTest by testSuite(
    displayName = "AssertionResult tests",
) {
    val subject = testFixture {
        AssertionSubject(
            subject = "subject",
        )
    }

    testSuite(name = "Cause") {
        test(name = "Cause returns the throwable when status is Failed with a cause") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            val exception = RuntimeException("Some exception")
            result.fail(cause = exception)

            expectThat(subject = result.cause)
                .isEqualTo(expected = exception)
        }

        test(name = "Cause returns null when status is Failed without a cause") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.fail()

            expectThat(subject = result.cause)
                .isNull()
        }

        test(name = "Cause returns null when status is Passed") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.pass()

            expectThat(subject = result.cause)
                .isNull()
        }
    }

    testSuite(name = "Failed") {
        test(name = "Failed returns true when status is Failed") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.fail()

            expectThat(subject = result.failed)
                .isEqualTo(expected = true)
        }

        test(name = "Failed returns false when status is Passed") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.pass()

            expectThat(subject = result.failed)
                .isEqualTo(expected = false)
        }
    }

    testSuite(name = "Status") {
        test(name = "Default status is Failed") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")

            expectThat(subject = result.status)
                .isA<AssertionResult.Status.Failed>()
        }

        test(name = "Status is Passed after calling pass") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.pass(description = "passed")

            expectThat(subject = result.status)
                .isA<AssertionResult.Status.Passed>()
        }

        test(name = "Status is Failed after calling fail") {
            val result = AssertionResult(parent = subject.invoke(), description = "description", expected = "expected")
            result.pass()
            result.fail(description = "failed")

            expectThat(subject = result.status)
                .isA<AssertionResult.Status.Failed>()
        }
    }
}
