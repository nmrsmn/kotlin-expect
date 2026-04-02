package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

@Suppress("UNCHECKED_CAST")
fun <T> Assertion.Builder<T?>.isNull(): Assertion.Builder<Nothing> =
    assert(description = "is null") {
        when (it) {
            null -> pass()
            else -> fail()
        }
    } as Assertion.Builder<Nothing>
