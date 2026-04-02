package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion

internal class AssertionBuilder<T>(
    override val subject: T,
) : Assertion.Builder<T> {
    override fun assert(
        description: String,
        assert: Assertion.(T) -> Unit,
    ): Assertion.Builder<T> = also {
        AssertionResult(description)
            .assert(subject)
    }
}
