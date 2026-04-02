package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion

internal class AssertionResult(
    private val description: String,
) : Assertion {
    override fun fail(description: String?, cause: Throwable?) {
        val message = buildString {
            append("Assertion failed: ${this@AssertionResult.description}")
        }
        throw AssertionError(message, cause)
    }

    override fun pass(description: String?) {
        // No-op: assertion passed successfully
    }
}
