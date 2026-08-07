package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

private const val MESSAGE_FAILURE_DESCRIPTION = "but message was: {}"

fun <T : Throwable> Assertion.Builder<T>.hasMessage(expected: String): Assertion.Builder<T> =
    assert(description = "has message {}", expected = expected) {
        when (it.message) {
            expected -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasMessageContaining(substring: String): Assertion.Builder<T> =
    assert(description = "has message containing {}", expected = substring) {
        when {
            it.message?.contains(substring) == true -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasMessageContaining(pattern: Regex): Assertion.Builder<T> =
    assert(description = "has message containing {}", expected = pattern) {
        when {
            it.message?.contains(pattern) == true -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasMessageStartingWith(prefix: String): Assertion.Builder<T> =
    assert(description = "has message starting with {}", expected = prefix) {
        when {
            it.message?.startsWith(prefix) == true -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasMessageEndingWith(suffix: String): Assertion.Builder<T> =
    assert(description = "has message ending with {}", expected = suffix) {
        when {
            it.message?.endsWith(suffix) == true -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasMessageMatching(pattern: Regex): Assertion.Builder<T> =
    assert(description = "has message matching {}", expected = pattern) {
        when {
            it.message?.matches(pattern) == true -> pass()

            else -> fail(
                description = MESSAGE_FAILURE_DESCRIPTION,
                actual = it.message,
            )
        }
    }

fun <T : Throwable> Assertion.Builder<T>.hasAnyCause(): Assertion.Builder<T> =
    assert(description = "has a cause") {
        when (it.cause) {
            null -> fail()
            else -> pass()
        }
    }

inline fun <reified C : Throwable> Assertion.Builder<out Throwable>.hasCause(): Assertion.Builder<C> =
    assert(description = "has cause of type: {}", expected = C::class) {
        when (val cause = it.cause) {
            is C -> pass()
            null -> fail(actual = null)
            else -> fail(actual = cause::class)
        }
    }
        .get(
            description = "cause of type: {}",
            function = { cause as C },
        )

fun <T : Throwable> Assertion.Builder<T>.hasNoCause(): Assertion.Builder<T> =
    assert(description = "has no cause") {
        when (val cause = it.cause) {
            null -> pass()

            else -> fail(
                description = "but cause was of type: {}",
                actual = cause::class,
            )
        }
    }
