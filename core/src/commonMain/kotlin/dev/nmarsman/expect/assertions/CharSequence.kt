package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion
import dev.nmarsman.expect.assertions.matches

fun <T : CharSequence?> Assertion.Builder<T>.isNullOrEmpty(): Assertion.Builder<T> =
    assertThat(description = "is null or empty") {
        it.isNullOrEmpty()
    }

fun <T : CharSequence> Assertion.Builder<T>.isEmpty(): Assertion.Builder<T> =
    assertThat(description = "is empty") {
        it.isEmpty()
    }

fun <T : CharSequence> Assertion.Builder<T>.isNotEmpty(): Assertion.Builder<T> =
    assertThat(description = "is not empty") {
        it.isNotEmpty()
    }

fun <T : CharSequence?> Assertion.Builder<T>.isNullOrBlank(): Assertion.Builder<T> =
    assertThat(description = "is null or blank") {
        it.isNullOrBlank()
    }

fun <T : CharSequence> Assertion.Builder<T>.isBlank(): Assertion.Builder<T> =
    assertThat(description = "is blank") {
        it.isBlank()
    }

fun <T : CharSequence> Assertion.Builder<T>.isNotBlank(): Assertion.Builder<T> =
    assertThat(description = "is not blank") {
        it.isNotBlank()
    }

fun <T : CharSequence> Assertion.Builder<T>.hasLength(expected: Int): Assertion.Builder<T> =
    assert(description = "has length {}", expected = expected) {
        when (it.length) {
            expected -> pass()
            else -> fail(actual = it.length)
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.startsWith(expected: Char): Assertion.Builder<T> =
    assert(description = "starts with {}", expected = expected) {
        when (it.startsWith(expected)) {
            true -> pass()
            else -> fail(actual = it.first())
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.startsWith(expected: CharSequence): Assertion.Builder<T> =
    assert(description = "starts with {}", expected = expected) {
        when (it.startsWith(expected)) {
            true -> pass()
            else -> fail(actual = it.take(expected.length))
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.endsWith(expected: Char): Assertion.Builder<T> =
    assert(description = "ends with {}", expected = expected) {
        when (it.endsWith(expected)) {
            true -> pass()
            else -> fail(actual = it.last())
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.endsWith(expected: CharSequence): Assertion.Builder<T> =
    assert(description = "ends with {}", expected = expected) {
        when (it.endsWith(expected)) {
            true -> pass()
            else -> fail(actual = it.takeLast(expected.length))
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.matches(expected: Regex): Assertion.Builder<T> =
    assert(description = "matches the regular expression {}", expected = expected) {
        when (it.matches(expected)) {
            true -> pass()
            else -> fail()
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.matchesIgnoringCase(expected: Regex): Assertion.Builder<T> =
    with(
        receiver = Regex(
            pattern = expected.pattern,
            option = RegexOption.IGNORE_CASE,
        ),
    ) {
        assert(description = "matches the regular expression {} (ignoring case)", expected = expected) {
            when (it.matches(this@with)) {
                true -> pass()
                else -> fail()
            }
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.contains(expected: Regex): Assertion.Builder<T> =
    assert(description = "contains a match for the regular expression {}", expected = expected) {
        when (it.contains(expected)) {
            true -> pass()
            else -> fail()
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.containsIgnoringCase(expected: Regex): Assertion.Builder<T> =
    with(
        receiver = Regex(
            pattern = expected.pattern,
            option = RegexOption.IGNORE_CASE,
        ),
    ) {
        assert(description = "contains a match for the regular expression {} (ignoring case)", expected = expected) {
            when (it.contains(this@with)) {
                true -> pass()
                else -> fail()
            }
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.contains(expected: CharSequence): Assertion.Builder<T> =
    assert(
        description = "contains {}",
        expected = expected,
    ) {
        when (it.contains(expected)) {
            true -> pass()
            else -> fail()
        }
    }

fun <T : CharSequence> Assertion.Builder<T>.containsIgnoringCase(expected: CharSequence): Assertion.Builder<T> =
    assert(
        description = "contains {} (ignoring case)",
        expected = expected,
    ) {
        when (it.contains(expected, ignoreCase = true)) {
            true -> pass()
            else -> fail()
        }
    }
