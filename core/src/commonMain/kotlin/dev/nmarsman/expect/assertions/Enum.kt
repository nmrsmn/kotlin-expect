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
