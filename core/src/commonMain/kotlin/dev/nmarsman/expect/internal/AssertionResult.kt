package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.exception.AssertionFailedException

internal class AssertionResult(
    private val description: String,
) : Assertion {
    override fun fail(description: String?, cause: Throwable?) {
        val message = buildString {
            append("Assertion failed: ${this@AssertionResult.description}")
        }
        throw AssertionFailedException(message, cause)
    }

    override fun pass(description: String?) {
        // No-op: assertion passed successfully
    }
}
