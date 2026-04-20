package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.internal.AssertionFailedMessageFormatter.formatValue

/**
 * Maps the assertion to an assertion on the entry value indexed by [key].
 *
 * @return An assertion on the value indexed by [key] or `null`
 *      if no such entry exists in the subject map.
 */
operator fun <T : Map<K, V>, K, V> Assertion.Builder<T>.get(key: K): Assertion.Builder<V?> =
    get(
        description = "entry [${formatValue(key)}]",
    ) {
        this[key]
    }

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
 * Asserts that the subject map contains an entry with the specified [key].
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

/**
 * Asserts that the subject map does not contain an entry with the specified [key].
 * Depending on the map implementation the value associated with [key] may be `null`.
 * This assertion just tests for the nonexistence of the key.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.doesNotContainKey(key: K): Assertion.Builder<T> =
    assertThat(
        description = "does not have an entry with the key {}",
        expected = key,
    ) {
        !it.containsKey(key)
    }

/**
 * Asserts that the subject map contains entries with the specified [keys].
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.containsKeys(vararg keys: K): Assertion.Builder<T> =
    compose(
        description = "has entries with the keys {}",
        expected = keys.toList(),
    ) {
        keys.forEach { key ->
            containsKey(key)
        }
    } require { all }

/**
 * Asserts that the subject map doesn't contain entries with the specified [keys].
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.doesNotContainKeys(vararg keys: K): Assertion.Builder<T> =
    compose(
        description = "doesn't have entries with the keys {}",
        expected = keys.toList(),
    ) {
        keys.forEach { key ->
            doesNotContainKey(key)
        }
    } require { all }

/**
 * Asserts that the subject map contains an entry with the specified [key],
 * with a value equal to [value].
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.hasEntry(key: K, value: V): Assertion.Builder<T> =
    hasEntry(key to value)

/**
 * Asserts that the subject map contains an entry with the specified [entry].key,
 * with a value equal to [entry].value.
 */
fun <T : Map<K, V>, K, V> Assertion.Builder<T>.hasEntry(entry: Pair<K, V>): Assertion.Builder<T> =
    apply {
        containsKey(entry.first)[entry.first]
            .isEqualTo(entry.second)
    }
