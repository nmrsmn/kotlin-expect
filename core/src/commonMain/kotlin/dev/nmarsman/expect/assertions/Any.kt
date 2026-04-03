package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

@Suppress("UNCHECKED_CAST")
inline fun <reified T> Assertion.Builder<*>.isA(): Assertion.Builder<T> = assert(
    description = "is an instance of {}",
    expected = T::class,
) {
    when (it) {
        is T -> pass()
        null -> fail(description = "null")
        else -> fail(description = it::class.simpleName)
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
