package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Throwable> Assertion.Builder<T>.hasMessage(expected: String): Assertion.Builder<T> =
    assert(description = "has message {}", expected = expected) {
        when (it.message) {
            expected -> pass()
            null -> fail(actual = null)
            else -> fail(actual = it.message)
        }
    }
