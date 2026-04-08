package dev.nmarsman.expect.internal

/**
 * Wraps the subject of an assertion with an optional description.
 *
 * When [description] is not null, it is used to describe the subject in
 * failure messages instead of the formatted representation of [subject].
 *
 * @param T The type of the subject.
 * @property parent The parent node in the assertion graph.
 * @property subject The actual subject value.
 * @property description An optional description to use when formatting the subject.
 */
internal class AssertionSubject<T>(
    override val parent: AssertionGroup<*>?,
    override val subject: T,
    override var description: String? = null,
) : AssertionGroup<T>, DescribableNode<T> {
    private val appendedChildren = mutableListOf<AssertionNode<*>>()
    override val children: Iterable<AssertionNode<*>> = appendedChildren

    override val root: AssertionGroup<*>
        // Suppressed to void generating unreachable extra branch for code coverage
        @Suppress("IfThenToElvis")
        get() = if (parent != null) parent.root else this

    init {
        parent?.also {
            it.append(this)
        }
    }

    constructor(subject: T) : this(parent = null, subject = subject)

    override fun append(node: AssertionNode<*>) {
        appendedChildren.add(node)
    }
}
