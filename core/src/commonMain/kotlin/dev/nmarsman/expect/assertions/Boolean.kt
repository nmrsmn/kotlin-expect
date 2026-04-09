package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Boolean?> Assertion.Builder<T>.isTrue(): Assertion.Builder<T> =
    assert(
        description = "is true",
        expected = true,
    ) {
        if (it == true) pass() else fail()
    }

fun <T : Boolean?> Assertion.Builder<T>.isFalse(): Assertion.Builder<T> =
    assert(
        description = "is false",
        expected = true,
    ) {
        if (it == false) pass() else fail()
    }
