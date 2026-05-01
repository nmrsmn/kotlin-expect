package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Enum<T>> Assertion.Builder<T>.isOneOf(vararg values: T): Assertion.Builder<T> =
    assert("is one of ${values.joinToString()}") {
        if (it in values) {
            pass()
        } else {
            fail()
        }
    }

/**
 * Maps an assertion on an enum to an assertion on its name.
 */
fun <T : Enum<T>> Assertion.Builder<T>.name(): Assertion.Builder<String> =
    get(Enum<T>::name)

/**
 * Maps an assertion on an enum to an assertion on its ordinal value.
 */
fun <T : Enum<T>> Assertion.Builder<T>.ordinal(): Assertion.Builder<Int> =
    get(Enum<T>::ordinal)
