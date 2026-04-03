package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.exception.AssertionFailedException

internal class AssertionBuilder<T>(
    override val subject: T,
) : Assertion.Builder<T> {
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
            val message = AssertionFailedMessageFormatter.format(subject, results)
            throw AssertionFailedException(message, result.cause)
        }
    }
}
