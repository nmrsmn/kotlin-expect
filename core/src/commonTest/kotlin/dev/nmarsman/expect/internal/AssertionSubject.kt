package dev.nmarsman.expect.internal

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.assertions.isEqualTo

val AssertionSubjectTest by testSuite(
    displayName = "Assertion subject tests",
) {
    test(name = "Assertion subject without parent should be flagged as root") {
        val subject = AssertionSubject(parent = null, subject = 42)

        expectThat(subject.root)
            .isEqualTo(subject)
    }

    test(name = "Assertion subject with parent shouldn't be flagged as root") {
        val root = AssertionSubject(subject = "Hello, world")
        val subject = AssertionSubject(parent = root, subject = 12)

        expectThat(subject.root)
            .isEqualTo(root)
    }

    test(name = "Assertion subject with grandparent shouldn't be flagged as root") {
        val root = AssertionSubject(subject = "Hello, world")
        val child = AssertionSubject(parent = root, subject = 12)
        val subject = AssertionSubject(parent = child, subject = 3)

        expectThat(subject.root)
            .isEqualTo(root)
    }
}
