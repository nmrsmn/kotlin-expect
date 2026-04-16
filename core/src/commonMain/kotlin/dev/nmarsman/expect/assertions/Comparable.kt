package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Comparable<T>> Assertion.Builder<T>.isGreaterThan(expected: T): Assertion.Builder<T> =
    assert(
        description = "is greater than {}",
        expected = expected,
    ) {
        when {
            it > expected -> pass()
            else -> fail()
        }
    }

fun <T : Comparable<T>> Assertion.Builder<T>.isLessThan(expected: T): Assertion.Builder<T> =
    assert(
        description = "is less than {}",
        expected = expected,
    ) {
        when {
            it < expected -> pass()
            else -> fail()
        }
    }

fun <T : Comparable<T>> Assertion.Builder<T>.isGreaterThanOrEqualTo(expected: T): Assertion.Builder<T> =
    assert(
        description = "is greater than or equal to {}",
        expected = expected,
    ) {
        when {
            it >= expected -> pass()
            else -> fail()
        }
    }

fun <T : Comparable<T>> Assertion.Builder<T>.isLessThanOrEqualTo(expected: T): Assertion.Builder<T> =
    assert(
        description = "is less than or equal to {}",
        expected = expected,
    ) {
        when {
            it <= expected -> pass()
            else -> fail()
        }
    }

fun <T : Comparable<T>> Assertion.Builder<T>.isIn(expected: ClosedRange<T>): Assertion.Builder<T> =
    assert(
        description = "is in the range {}",
        expected = expected,
    ) {
        when (it) {
            in expected -> pass()
            else -> fail()
        }
    }
