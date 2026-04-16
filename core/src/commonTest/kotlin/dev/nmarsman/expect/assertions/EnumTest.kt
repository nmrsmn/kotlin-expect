package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.exception.AssertionFailedException

private enum class Direction {
    NORTH,
    SOUTH,
    WEST,
    EAST,
}

val EnumAssertionTest by testSuite(
    displayName = "Enum assertion tests",
) {
    testSuite(name = "`isOneOf` assertions") {
        test(name = "Passes if the subject is one of the expected values") {
            expectThat(Direction.NORTH)
                .isOneOf(Direction.NORTH, Direction.WEST, Direction.EAST)
        }

        test(name = "Fails if the subject is not one of the expected values") {
            expectThrows<AssertionFailedException> {
                expectThat(Direction.NORTH)
                    .isOneOf(Direction.SOUTH, Direction.WEST)
            }
        }
    }
}
