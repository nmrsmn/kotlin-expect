package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.internal.AssertionFailedMessageFormatter.describe
import dev.nmarsman.expect.internal.AssertionResult
import dev.nmarsman.expect.internal.AssertionSubject

val DescribeAssertionNodeTest by testSuite(
    displayName = "Describe assertion node tests",
) {
    testSuite(
        name = "Describe assertion subject",
    ) {
        test(name = "Describe should properly format Int subject") {
            val subject = AssertionSubject(subject = 42)

            expectThat(subject = subject.describe())
                .isEqualTo("▼ Expect that 42:")
        }

        test(name = "Describe should properly format String subject") {
            val subject = AssertionSubject(subject = "Hello, world")

            expectThat(subject = subject.describe())
                .isEqualTo("▼ Expect that \"Hello, world\":")
        }

        test(name = "Describe should properly format subject with description") {
            val subject = AssertionSubject(subject = 42).apply {
                description = "fourty two"
            }

            expectThat(subject = subject.describe())
                .isEqualTo("▼ Expect that fourty two:")
        }

        test(name = "Describe should properly format description of nested subject") {
            val parent = AssertionSubject(subject = "Hello, world")
            val subject = AssertionSubject(parent = parent, subject = 12).also {
                it.description = "value of the property length ({})"
            }

            expectThat(subject = subject.describe())
                .isEqualTo("▼ value of the property length (12):")
        }
    }

    testSuite(
        name = "Describe assertion result",
    ) {
        test(name = "Describe should properly format failed assertion result - without failure description") {
            val subject = AssertionSubject(subject = 42)
            val result = AssertionResult.AtomicResult(
                parent = subject,
                description = "is equal to {}",
                expected = 1,
            ).also {
                it.fail()
            }

            expectThat(subject = result.describe())
                .isEqualTo("✗ is equal to 1")
        }

        test(name = "Describe should properly format failed assertion result - with failure description") {
            val subject = AssertionSubject(subject = 42)
            val result = AssertionResult.AtomicResult(
                parent = subject,
                description = "is equal to {}",
                expected = 1,
            ).also {
                it.fail(description = "but was Int")
            }

            expectThat(subject = result.describe())
                .isEqualTo("✗ is equal to 1")
        }

        test(name = "Describe should properly format passed assertion result - without description") {
            val subject = AssertionSubject(subject = 42)
            val result = AssertionResult.AtomicResult(
                parent = subject,
                description = "is equal to {}",
                expected = 1,
            ).also {
                it.pass()
            }

            expectThat(subject = result.describe())
                .isEqualTo("✓ is equal to 1")
        }

        test(name = "Describe should properly format passed assertion result - with description") {
            val subject = AssertionSubject(subject = 42)
            val result = AssertionResult.AtomicResult(
                parent = subject,
                description = "is equal to {}",
                expected = 1,
            ).also {
                it.pass(description = "but was Int")
            }

            expectThat(subject = result.describe())
                .isEqualTo("✓ is equal to 1")
        }
    }
}
