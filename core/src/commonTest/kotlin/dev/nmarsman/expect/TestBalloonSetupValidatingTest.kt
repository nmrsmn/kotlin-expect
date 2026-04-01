package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import kotlin.test.assertEquals

val testBalloonSetupValidatingTest by testSuite {
    test("validating the setup") {
        assertEquals(1, 1)
    }
}
