package dev.nmarsman.expect.api

import dev.nmarsman.expect.internal.AssertionBuilder

/**
 * Starts an assertion on the given [subject] and returns a builder for chaining further assertions.
 * This is the entry-point for the assertion API, allowing you to create assertions on any value.
 *
 * @param T The type of the subject being asserted on.
 * @param subject The value ([T]) to be asserted on.
 * @return An assertion on the value of [subject].
 */
fun <T> expectThat(subject: T): Assertion.Builder<T> =
    AssertionBuilder(subject)
