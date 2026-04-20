package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

/**
 * Assert that the subject map is empty.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.isEmpty(): Assertion.Builder<T> =
    assertThat(
        description = "is empty",
        assert = Map<K, V>::isEmpty,
    )

/**
 * Assert that the subject map is not empty.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.isNotEmpty(): Assertion.Builder<T> =
    assertThat(
        description = "is not empty",
        assert = Map<K, V>::isNotEmpty,
    )

/**
 * Asserts that the subject map has the specified number of entries.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.hasSize(expected: Int): Assertion.Builder<T> =
    assert(
        description = "has size {}",
        expected = expected,
    ) {
        when (it.size) {
            expected -> pass(actual = it.size)
            else -> fail(actual = it.size)
        }
    }

/**
 * Asserts that hte subject map contains an entry with the specified [key].
 * Depending on the map implementation the value associated with [key] may be `null`.
 * This assertion just tests for the existence of the key.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.containsKey(key: K): Assertion.Builder<T> =
    assertThat(
        description = "has an entry with the key {}",
        expected = key,
    ) {
        it.containsKey(key)
    }
