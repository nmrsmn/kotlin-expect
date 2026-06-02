@file:Suppress("StringLiteralDuplication")

package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion
import kotlin.math.absoluteValue

fun Assertion.Builder<Double>.isEqualTo(expected: Double, tolerance: Double): Assertion.Builder<Double> = assert(
    description = "is within $tolerance of $expected",
    expected = expected,
) {
    val difference = it - expected

    if (difference.absoluteValue < tolerance) {
        pass(
            actual = it,
            description = "differs by $difference",
        )
    } else {
        fail(
            actual = it,
            description = "differs by $difference",
        )
    }
}

fun Assertion.Builder<Float>.isEqualTo(expected: Float, tolerance: Float): Assertion.Builder<Float> = assert(
    description = "is within $tolerance of $expected",
    expected = expected,
) {
    val difference = it - expected

    if (difference.absoluteValue < tolerance) {
        pass(
            actual = it,
            description = "differs by $difference",
        )
    } else {
        fail(
            actual = it,
            description = "differs by $difference",
        )
    }
}
