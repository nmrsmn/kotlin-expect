package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion.AtomicAssertion

internal class AssertionResult<S>(
    override val parent: AssertionGroup<S>,
    override var description: String? = null,
    val expected: Any? = null,
) : DescribableNode<S>, AtomicAssertion {
    override val subject: S
        get() = parent.subject

    override val root: AssertionGroup<*>
        get() = parent.root

    sealed interface Status {
        val symbol: String

        data object Pending : Status {
            override val symbol: String
                get() = "…"
        }

        data class Passed(
            val description: String?,
            val actual: Any?,
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

    var status: Status = Status.Pending
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
        status = Status.Passed(description, null)
    }

    override fun pass(description: String?, actual: Any?) {
        status = Status.Passed(description, actual)
    }
}
