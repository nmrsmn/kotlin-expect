package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion

internal class AssertionBuilder<T>(
    private val context: AssertionGroup<T>,
    private val strategy: AssertionStrategy = AssertionStrategy.Throwing,
) : Assertion.Builder<T> {
    override val subject: T
        get() = context.subject

    override fun assert(
        description: String,
        expected: Any?,
        assert: Assertion.(T) -> Unit,
    ): Assertion.Builder<T> = also {
        val result = AssertionResult(
            parent = context,
            description = description,
            expected = expected,
        ).apply {
            assert.invoke(this, subject)
            context.append(this)
        }

        strategy.evaluate(result)
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
}
