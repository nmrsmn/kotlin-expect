package dev.nmarsman.expect.api

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
            assert: Assertion.(T) -> Unit,
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
            assert: Assertion.(T) -> Unit,
        ): Builder<T>
    }
}
