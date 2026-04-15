package dev.nmarsman.expect.api

import dev.nmarsman.expect.internal.describe

/**
 * Allows assertions to be made on the subject of an expectation.
 * This is the main interface for making assertions in the Kotlin Expect library.
 * It provides a fluent API for writing assertions in a readable and expressive way.
 */
interface Assertion {

    /**
     * Mark the result of the assertion as failed.
     *
     * @param description An optional description of the failure. Can provide context about why the assertion failed.
     * @param cause An optional Throwable that caused the failure. Can be used to provide more detailed information
     *      about the failure, such as an exception that was thrown during the assertion.
     */
    fun fail(description: String? = null, cause: Throwable? = null)

    /**
     * Mark the result of the assertion as successful.
     *
     * @param description An optional description of the success. Can provide context about why the assertion passed.
     */
    fun pass(description: String? = null)

    /**
     * An assertion of a single condition, that can be marked as passed or failed with an actual value.
     */
    interface AtomicAssertion : Assertion {

        /**
         * Mark the result of the assertion as failed, including an actual value.
         *
         * When the [description] contains `{}`, it will be replaced with the formatted representation of [actual].
         *
         * @param description A description of the failure. If it contains `{}`, the placeholder will be replaced with
         *      the formatted representation of [actual].
         * @param actual The actual value that was encountered. Will be formatted and used to
         *      replace `{}` in [description], if present.
         * @param cause An optional Throwable that caused the failure.
         */
        fun fail(
            description: String = "but was: {}",
            actual: Any?,
            cause: Throwable? = null,
        )

        /**
         * Mark the result of the assertion as successful.
         *
         * @param description An optional description of the success.
         *      Can provide context about why the assertion passed.
         * @param actual The actual value that was encountered. Will be formatted and used to
         *      replace `{}` in [description], if present.
         */
        fun pass(
            description: String? = "but was: {}",
            actual: Any?,
        )
    }

    /**
     * An assertion composed of multiple conditions whose overall result
     * is determined by aggregation of those conditions' results.
     */
    interface ComposedAssertion : Assertion {

        /**
         * @property all True if all composed assertions passed, otherwise false.
         */
        val all: Boolean

        /**
         * @property any True if at least one composed assertion passed, otherwise false.
         */
        val any: Boolean

        /**
         * @property none True if all composed assertions failed, otherwise false.
         */
        val none: Boolean

        /**
         * @property failedCount The number of composed assertions that failed.
         */
        val failedCount: Int

        /**
         * @property passedCount The number of composed assertions that passed.
         */
        val passedCount: Int
    }

    /**
     * Used to construct assertions.
     *
     * @param T the type of the subject being asserted on.
     */
    interface Builder<T> {
        val subject: T

        /**
         * Defines an assertion on the subject of the expectation. The assertion is defined as a lambda function that
         * takes an instance of [Assertion] and the subject of type [T] as parameters.
         *
         * @param description A description of the assertion. Can provide context about what is being asserted.
         * @param assert The lambda function that defines the assertion logic. It receives an instance of
         *      [Assertion] and the subject of type [T], allowing you to perform assertions on the
         *      subject and mark the assertion as passed or failed using the [Assertion] instance.
         */
        fun assert(
            description: String,
            assert: AtomicAssertion.(T) -> Unit,
        ): Builder<T> = assert(
            description = description,
            expected = null,
            assert = assert,
        )

        /**
         * Defines an assertion on the subject of the expectation. The assertion is defined as a lambda function that
         * takes an instance of [Assertion] and the subject of type [T] as parameters.
         *
         * @param description A description of the assertion. Can provide context about what is being asserted.
         * @param expected The expected value of the comparison.
         * @param assert The lambda function that defines the assertion logic. It receives an instance of
         *      [Assertion] and the subject of type [T], allowing you to perform assertions on the
         *      subject and mark the assertion as passed or failed using the [Assertion] instance.
         */
        fun assert(
            description: String,
            expected: Any?,
            assert: AtomicAssertion.(T) -> Unit,
        ): Builder<T>

        /**
         * Evaluates a boolean condition.
         * This is a helper method for implementing simple type of assertions.
         *
         * @param description A description for the condition of the assertion.
         * @param assert A function that returns true, if the assertion passes.
         *      false, if the assertion fails.
         * @return The chained assertion builder.
         */
        fun assertThat(
            description: String,
            assert: (T) -> Boolean,
        ): Builder<T> = assert(
            description = description,
        ) {
            if (assert(it)) pass() else fail()
        }

        /**
         * Allows an assertion to be composed of multiple assertions.
         *
         * @param description A description for the condition the assertion evaluates.
         * @param assertions A group of assertions that will be evaluated.
         */
        fun compose(
            description: String,
            assertions: Builder<T>.() -> Unit,
        ) = compose(
            description = description,
            expected = null,
            assertions = assertions,
        )

        /**
         * Allows an assertion to be composed of multiple assertions.
         *
         * @param description A description for the condition the assertion evaluates.
         * @param expected The expected value of the comparison.
         * @param assertions A group of assertions that will be evaluated.
         */
        fun compose(
            description: String,
            expected: Any?,
            assertions: Builder<T>.() -> Unit,
        ): ComposedBuilder<T>

        /**
         * Sets a custom description for the subject of this assertion.
         * When set, this description is used in failure messages instead of the formatted subject value.
         *
         * @param description The description to use for the subject.
         * @return This builder for chaining.
         */
        fun describedAs(description: String): Builder<T>

        /**
         * Sets a custom description for the subject of this assertion using a lambda.
         * When set, this description is used in failure messages instead of the formatted subject value.
         *
         * @param descriptor A lambda that returns the description to use for the subject.
         * @return This builder for chaining.
         */
        fun describedAs(descriptor: () -> String): Builder<T> =
            describedAs(descriptor.invoke())

        /**
         * Sets a custom description for the subject of this assertion using a formatted value.
         * The value is formatted using the same formatting rules as assertion descriptions and expected values.
         *
         * @param value The value to format and use as the subject description.
         * @return This builder for chaining.
         */
        fun describedAs(value: Any): Builder<T>

        /**
         * Maps the assertion to the result of the [function].
         *
         * @param R The type of the result returned by [function].
         * @param function A lambda whose receiver is the current assertion.
         * @return An assertion builder whose subject is the value returned by [function].
         */
        fun <R> get(function: T.() -> R): Builder<R> =
            get(
                description = function.describe(),
                function = function,
            )

        /**
         * Maps the assertion to the result of the [function].
         *
         * @param R The type of the result returned by [function].
         * @param description The description of the mapped result.
         * @param function A lambda whose receiver is the current assertion.
         * @return An assertion builder whose subject is the value returned by [function].
         */
        fun <R> get(description: String? = null, function: T.() -> R): Builder<R>

        /**
         * Maps the assertion to the result of the [function].
         *
         * @param R The type of the result returned by [function].
         * @param function A lambda whose receiver is the current assertion.
         * @param assertions A group of assertions that will be evaluated on the result of [function].
         */
        fun <R> with(function: T.() -> R, assertions: Builder<R>.() -> Unit): Builder<T> =
            with(
                description = function.describe(),
                function = function,
                assertions = assertions,
            )

        /**
         * Maps the assertion to the result of the [function].
         *
         * @param R The type of the result returned by [function].
         * @param description The description of the mapped result.
         * @param function A lambda whose receiver is the current assertion.
         * @param assertions A group of assertions that will be evaluated on the result of [function].
         */
        fun <R> with(
            description: String? = null,
            function: T.() -> R,
            assertions: Builder<R>.() -> Unit,
        ): Builder<T>
    }

    /**
     * Used to construct composed assertions, which are assertions that consist of multiple conditions.
     */
    interface ComposedBuilder<T> {

        /**
         * Determines the overall result of the composed assertion based on the results of its individual assertions.
         */
        infix fun then(block: ComposedAssertion.() -> Unit): Builder<T>

        /**
         * Determines the overall result of the composed assertion based on the results of its individual assertions.
         */
        infix fun require(block: ComposedAssertion.() -> Boolean): Builder<T> =
            then {
                when (block.invoke(this)) {
                    true -> pass()
                    else -> fail()
                }
            }
    }
}
