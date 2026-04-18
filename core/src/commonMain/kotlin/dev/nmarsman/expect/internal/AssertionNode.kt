package dev.nmarsman.expect.internal

/**
 * Represents a node in the assertion tree.
 *
 * An assertion tree is built up as assertions are chained and subjects are
 * transformed. Each node is either a [AssertionResult] or a [AssertionSubject] or a
 * that nests further assertions under a transformed subject.
 */
internal sealed interface AssertionNode<S> {
    val subject: S
    val parent: AssertionGroup<*>?
    val root: AssertionGroup<*>
}

/**
 * Represents a describable assertion node.
 */
internal sealed interface DescribableNode<S> : AssertionNode<S> {
    var description: String?
}

/**
 * Represents a group of assertions that share a common subject.
 */
internal sealed interface AssertionGroup<S> : AssertionNode<S> {
    val children: Collection<AssertionNode<*>>

    fun append(node: AssertionNode<*>)
}
