package dev.nmarsman.expect.internal

import dev.nmarsman.expect.exception.AssertionFailedException

internal sealed interface AssertionStrategy {

    /**
     * Evaluates an assertion result in the context of the strategy.
     *
     * @param T The type of the assertion group context.
     * @param result The assertion result to evaluate.
     */
    fun <T> evaluate(result: AssertionResult<T>)

    /**
     * A strategy that throws an [AssertionFailedException] immediately when an assertion fails.
     * This is the default strategy used by `expectThat`.
     */
    data object Throwing : AssertionStrategy {
        override fun <T> evaluate(result: AssertionResult<T>) {
            if (result.failed) {
                val message = AssertionFailedMessageFormatter.format(context = result.root)
                throw AssertionFailedException(
                    message = message,
                    expected = result.expected,
                    actual = result.subject,
                    cause = result.cause,
                )
            }
        }
    }

    /**
     * A strategy that collects assertion failures instead of throwing immediately.
     * Failures are accumulated in the assertion tree and can be thrown together
     * at the end using [throwCollectedFailures].
     */
    data object Collecting : AssertionStrategy {
        private val AssertionGroup<*>.failed: Boolean
            get() = children.any { node ->
                when (node) {
                    is AssertionResult<*> -> node.failed
                    is AssertionGroup<*> -> node.failed
                }
            }

        override fun <T> evaluate(result: AssertionResult<T>) {
            // Do nothing — failures are kept in the assertion tree
        }

        /**
         * Throws an [AssertionFailedException] if any failures exist in the assertion [context] tree.
         * The exception message is formatted from the entire tree, showing all passed and failed results.
         */
        fun <T> throwCollectedFailures(context: AssertionGroup<T>) {
            if (!context.failed) return

            val message = AssertionFailedMessageFormatter.format(context = context.root)
            throw AssertionFailedException(message = message, cause = null)
        }
    }
}
