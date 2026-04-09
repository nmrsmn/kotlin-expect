package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Assertion.Builder<*>.isA(): Assertion.Builder<T> = assert(
    description = "is an instance of {}",
    expected = T::class,
) {
    when (it) {
        is T -> pass()
        null -> fail(actual = null)
        else -> fail(actual = it::class)
    }
} as Assertion.Builder<T>

fun <T> Assertion.Builder<T>.isEqualTo(expected: T?): Assertion.Builder<T> = assert(
    description = "is equal to {}",
    expected = expected,
) {
    when (it) {
        expected -> pass()
        else -> fail()
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Assertion.Builder<T?>.isNull(): Assertion.Builder<Nothing> = assert(
    description = "is null",
) {
    when (it) {
        null -> pass()
        else -> fail()
    }
} as Assertion.Builder<Nothing>

@Suppress("UNCHECKED_CAST")
fun <T> Assertion.Builder<T?>.isNotNull(): Assertion.Builder<T> =
    assert(
        description = "is not null",
    ) {
        when (it) {
            null -> fail()
            else -> pass()
        }
    } as Assertion.Builder<T>
