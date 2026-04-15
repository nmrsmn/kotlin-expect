package dev.nmarsman.expect.internal

import dev.nmarsman.expect.api.Assertion

internal sealed interface AssertionResult<T> : DescribableNode<T>, Assertion {
    override val parent: AssertionGroup<*>

    override val root: AssertionGroup<*>
        get() = parent.root

    val expected: Any?

    val status: Status
        get() = Status.Pending

    val failed: Boolean
        get() = status is Status.Failed

    class AtomicResult<T>(
        override val parent: AssertionGroup<T>,
        override var description: String? = null,
        override val expected: Any? = null,
    ) : AssertionResult<T>, Assertion.AtomicAssertion {
        override val subject: T
            get() = parent.subject

        override var status: Status = Status.Pending
            private set

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

    class ComposedResult<T>(
        override val parent: AssertionGroup<T>,
        override var description: String? = null,
        override val expected: Any? = null,
    ) : AssertionResult<T>, AssertionGroup<T>, Assertion.ComposedAssertion {
        override val subject: T
            get() = parent.subject

        override var status: Status = Status.Pending
            private set

        private val appendedChildren = mutableListOf<AssertionNode<*>>()
        override val children: Iterable<AssertionNode<*>> = appendedChildren

        private val results: List<AssertionResult<*>>
            get() = collectResults(children)

        override val all: Boolean
            get() = results.all { !it.failed }

        override val any: Boolean
            get() = results.any { !it.failed }

        override val none: Boolean
            get() = results.all { it.failed }

        override val passedCount: Int
            get() = results.count { !it.failed }

        override val failedCount: Int
            get() = results.count { it.failed }

        override fun append(node: AssertionNode<*>) {
            appendedChildren.add(node)
        }

        override fun fail(description: String?, cause: Throwable?) {
            status = Status.Failed(description, null, cause)
        }

        override fun pass(description: String?) {
            status = Status.Passed(description, null)
        }

        private fun collectResults(nodes: Iterable<AssertionNode<*>>): List<AssertionResult<*>> =
            nodes.flatMap { node ->
                when (node) {
                    is AssertionResult<*> -> listOf(node)
                    is AssertionGroup<*> -> collectResults(node.children)
                }
            }
    }

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
}
