package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.exception.AssertionFailedException

internal class AssertionBuilder<T>(
    private var context: AssertionSubject<T>,
) : Assertion.Builder<T> {
    override val subject: T
        get() = context.subject

    private val results = mutableListOf<AssertionResult>()

    override fun assert(
        description: String,
        expected: Any?,
        assert: Assertion.(T) -> Unit,
    ): Assertion.Builder<T> = also {
        val result = AssertionResult(description, expected)
            .also { it.assert(subject) }
            .also { results.add(it) }

        if (result.failed) {
            val message = AssertionFailedMessageFormatter.format(context, results)
            throw AssertionFailedException(message, result.cause)
        }
    }

    override fun describedAs(description: String): Assertion.Builder<T> = also {
        context = context.copy(
            description = description,
        )
    }

    override fun describedAs(value: Any): Assertion.Builder<T> = also {
        context = context.copy(
            description = AssertionFailedMessageFormatter.formatValue(value)
                .toString(),
        )
    }

    override fun <R> get(description: String?, function: T.() -> R): Assertion.Builder<R> {
        val transformed = function.invoke(context.subject)

        return AssertionBuilder(
            context = AssertionSubject(
                subject = transformed,
                description = description,
            ),
        )
    }
}
