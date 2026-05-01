package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Collection<E>, E> Assertion.Builder<T>.hasSize(expected: Int): Assertion.Builder<T> =
    assert(
        description = "has size {}",
        expected = expected,
    ) {
        when (it.size) {
            expected -> pass(actual = it.size)
            else -> fail(actual = it.size)
        }
    }

fun <T : Collection<E>, E> Assertion.Builder<T>.isEmpty(): Assertion.Builder<T> =
    assertThat(
        description = "is empty",
        assert = Collection<E>::isEmpty,
    )

fun <T : Collection<E>, E> Assertion.Builder<T>.isNotEmpty(): Assertion.Builder<T> =
    assertThat(
        description = "is not empty",
        assert = Collection<E>::isNotEmpty,
    )

/**
 * Maps an assertion subject on a collection to an assertion to its size.
 */
fun <T: Collection<*>> Assertion.Builder<T>.size(): Assertion.Builder<Int> =
    get(Collection<*>::size)
