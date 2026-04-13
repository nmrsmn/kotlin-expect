package dev.nmarsman.expect.api

import dev.nmarsman.expect.internal.AssertionBuilder
import dev.nmarsman.expect.internal.AssertionStrategy
import dev.nmarsman.expect.internal.AssertionSubject

/**
 * Starts an assertion on the given [subject] and returns a builder for chaining further assertions.
 * This is the entry-point for the assertion API, allowing you to create assertions on any value.
 *
 * @param T The type of the subject being asserted on.
 * @param subject The value ([T]) to be asserted on.
 * @return An assertion on the value of [subject].
 */
fun <T> expectThat(subject: T): Assertion.Builder<T> =
    AssertionBuilder(
        context = AssertionSubject(subject),
        strategy = AssertionStrategy.Throwing,
    )

/**
 * Asserts that [block] throws an exception of type [T] when executed.
 *
 * @return an assertion over the thrown exception, allowing further assertions
 * about messages, root causes, etc.
 */
@Suppress("UNCHECKED_CAST", "TooGenericExceptionCaught")
inline fun <reified T : Throwable> expectThrows(crossinline block: () -> Unit): Assertion.Builder<T> =
    expectThat(
        subject = try {
            block.invoke()
        } catch (throwable: Throwable) {
            throwable
        },
    )
        .describedAs(T::class)
        .assert(description = "is thrown,") {
            when (it) {
                is T -> pass()
                !is Throwable -> fail(description = "but no exception is thrown")
                else -> fail(description = "but threw {}", actual = it::class)
            }
        } as Assertion.Builder<T>

/**
 * Starts an assertion on the given [subject] and executes [block] using a collecting strategy
 * that accumulates all assertion failures and throws them together at the end.
 *
 * This is useful for "soft assertions" where you want to see all failures
 * at once rather than stopping at the first failure.
 *
 * @param T The type of the subject being asserted on.
 * @param subject The value ([T]) to be asserted on.
 * @param block A lambda with an [Assertion.Builder] receiver containing assertions on [subject].
 * @throws dev.nmarsman.expect.exception.AssertionFailedException if any assertions in [block] fail.
 */
fun <T> expectThat(subject: T, block: Assertion.Builder<T>.() -> Unit): Assertion.Builder<T> =
    AssertionSubject(subject).let { context ->
        with(AssertionStrategy.Collecting) {
            AssertionBuilder(
                context = context,
                strategy = this,
            ).apply {
                block.invoke(this)
                throwCollectedFailures(context)
            }
        }
    }
