package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion

internal class AssertionResult(
    val description: String,
    val expected: Any?,
) : Assertion {
    sealed interface Status {
        val symbol: String

        data class Passed(
            val description: String?,
        ) : Status {
            override val symbol: String
                get() = "✓"
        }

        data class Failed(
            val description: String?,
            val actual: Any?,
            val cause: Throwable?,
        ) : Status {
            override val symbol: String
                get() = "✗"
        }
    }

    var status: Status = Status.Failed(null, null, null)
        private set

    val failed: Boolean
        get() = status is Status.Failed

    val cause: Throwable?
        get() = (status as? Status.Failed)?.cause

    override fun fail(description: String?, cause: Throwable?) {
        status = Status.Failed(description, null, cause)
    }

    override fun fail(description: String, actual: Any?, cause: Throwable?) {
        status = Status.Failed(description, actual, cause)
    }

    override fun pass(description: String?) {
        status = Status.Passed(description)
    }
}
