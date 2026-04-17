package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.api.Assertion.AtomicAssertion
import dev.nmarsman.expect.internal.AssertionStrategy.Collecting.throwCollectedFailures

internal class AssertionBuilder<T>(
    private val context: AssertionGroup<T>,
    private val strategy: AssertionStrategy = AssertionStrategy.Throwing,
) : Assertion.Builder<T> {
    override val subject: T
        get() = context.subject

    override fun assert(
        description: String,
        expected: Any?,
        assert: AtomicAssertion.(T) -> Unit,
    ): Assertion.Builder<T> = also {
        val result = AssertionResult.AtomicResult(
            parent = context,
            description = description,
            expected = expected,
        ).apply {
            assert.invoke(this, subject)
            context.append(this)
        }

        strategy.evaluate(result)
    }

    override fun compose(
        description: String,
        expected: Any?,
        assertions: Assertion.Builder<T>.() -> Unit,
    ): Assertion.ComposedBuilder<T> {
        val result = AssertionResult.ComposedResult(
            parent = context,
            description = description,
            expected = expected,
        ).apply {
            context.append(this)
        }

        val builder = AssertionBuilder(
            context = result,
            strategy = AssertionStrategy.Collecting,
        ).apply {
            assertions.invoke(this)
        }

        return object : Assertion.ComposedBuilder<T> {
            override fun then(block: Assertion.ComposedAssertion.() -> Unit): Assertion.Builder<T> =
                this@AssertionBuilder.apply {
                    block.invoke(builder.context as Assertion.ComposedAssertion)
                    if (strategy is AssertionStrategy.Throwing) {
                        throwCollectedFailures(context)
                    }
                }
        }
    }

    override fun describedAs(description: String): Assertion.Builder<T> = also {
        (context as DescribableNode<*>).description = description
    }

    override fun describedAs(value: Any): Assertion.Builder<T> =
        describedAs(
            description = AssertionFailedMessageFormatter
                .formatValue(value)
                .toString(),
        )

    override fun <R> get(description: String?, function: T.() -> R): Assertion.Builder<R> {
        val transformed = function.invoke(context.subject)

        return AssertionBuilder(
            context = AssertionSubject(
                parent = context,
                subject = transformed,
                description = description,
            ),
            strategy = strategy,
        )
    }

    override fun <R> with(
        description: String?,
        function: T.() -> R,
        assertions: Assertion.Builder<R>.() -> Unit,
    ): Assertion.Builder<T> {
        val transformed = function.invoke(context.subject)
        val subject = AssertionSubject(
            parent = context,
            subject = transformed,
            description = description,
        )

        AssertionBuilder(
            context = subject,
            strategy = AssertionStrategy.Collecting,
        ).apply {
            assertions.invoke(this)
            throwCollectedFailures(context)
        }

        return this
    }
}
