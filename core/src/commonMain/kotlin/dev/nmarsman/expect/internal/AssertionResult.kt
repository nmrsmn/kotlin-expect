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
            val cause: Throwable?,
        ) : Status {
            override val symbol: String
                get() = "✗"
        }
    }

    var status: Status = Status.Failed(null, null)
        private set

    val failed: Boolean
        get() = status is Status.Failed

    val cause: Throwable?
        get() = (status as? Status.Failed)?.cause

    override fun fail(description: String?, cause: Throwable?) {
        status = Status.Failed(description, cause)
    }

    override fun pass(description: String?) {
        status = Status.Passed(description)
    }
}
