package dev.nmarsman.expect.internal

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException
import dev.nmarsman.expect.internal.AssertionStrategy.Collecting.throwCollectedFailures

val AssertionStrategyTest by testSuite(
    displayName = "Assertion strategy tests",
) {
    testSuite(name = "Throwing strategy") {
        test(name = "Should not throw when assertion passed") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::pass)
                .also { context.append(it) }

            AssertionStrategy.Throwing.evaluate(result = result)
        }

        test(name = "Should throw when assertion fails") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::fail)
                .also { context.append(it) }

            expectThrows<AssertionFailedException> {
                AssertionStrategy.Throwing.evaluate(result = result)
            }
        }
    }

    testSuite(name = "Collecting strategy") {
        test(name = "Should not throw when assertion passed - without collecting failures explicitly") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::pass)
                .also { context.append(it) }

            AssertionStrategy.Collecting.evaluate(result = result)
        }

        test(name = "Should not throw when assertion pass - with collecting failures explicitly") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::pass)
                .also { context.append(it) }

            AssertionStrategy.Collecting.evaluate(result = result)
            throwCollectedFailures(context)
        }

        test(name = "Should not throw when assertion fails - without collecting failures explicitly") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::fail)
                .also { context.append(it) }

            AssertionStrategy.Collecting.evaluate(result = result)
        }

        test(name = "Should throw when assertion fails - with collecting failures explicitly") {
            val context = AssertionSubject(subject = "subject")
            val result = AssertionResult(parent = context)
                .also(AssertionResult<*>::fail)
                .also { context.append(it) }

            AssertionStrategy.Collecting.evaluate(result = result)

            expectThrows<AssertionFailedException> {
                throwCollectedFailures(context)
            }
        }

        testSuite(name = "Chained assertions") {
            test(name = "Should throw when a nested group contains a failing assertion") {
                val root = AssertionSubject(subject = "subject")

                AssertionSubject(parent = root, subject = 42).apply {
                    append(
                        node = AssertionResult(parent = this)
                            .also(AssertionResult<*>::fail),
                    )
                }

                expectThrows<AssertionFailedException> {
                    throwCollectedFailures(root)
                }
            }

            test(name = "Should not throw when a nested group contains only passing assertions") {
                val root = AssertionSubject(subject = "subject")

                AssertionSubject(parent = root, subject = 42).apply {
                    append(
                        node = AssertionResult(parent = this)
                            .also(AssertionResult<*>::pass),
                    )
                }

                throwCollectedFailures(root)
            }
        }
    }
}
