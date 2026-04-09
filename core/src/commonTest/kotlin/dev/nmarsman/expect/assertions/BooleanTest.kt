package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows

val BooleanTest by testSuite(
    displayName = "Boolean assertion tests",
) {
    testSuite(name = "`isTrue` assertion") {
        test(name = "Passes when the subject is true") {
            expectThat(true)
                .isTrue()
        }

        test(name = "Fails when the subject is false") {
            expectThrows<AssertionError> {
                expectThat(false)
                    .isTrue()
            }
        }

        test(name = "Fails when the subject is `null`") {
            expectThrows<AssertionError> {
                expectThat(null)
                    .isTrue()
            }
        }

        test(name = "A failing assertion should properly format the subject") {
            expectThrows<AssertionError> {
                expectThat(false)
                    .isTrue()
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that false:
                            |   ✗ is true
                        """.trimMargin(),
                    )
            }
        }
    }

    testSuite(name = "`isFalse` assertion") {
        test(name = "Passes when the subject is false") {
            expectThat(false)
                .isFalse()
        }

        test(name = "Fails when the subject is true") {
            expectThrows<AssertionError> {
                expectThat(true)
                    .isFalse()
            }
        }

        test(name = "Fails when the subject is `null`") {
            expectThrows<AssertionError> {
                expectThat(null)
                    .isFalse()
            }
        }

        test(name = "A failing assertion should properly format the subject") {
            expectThrows<AssertionError> {
                expectThat(true)
                    .isFalse()
            }.let {
                expectThat(it.subject.message)
                    .isEqualTo(
                        """
                            |▼ Expect that true:
                            |   ✗ is false
                        """.trimMargin(),
                    )
            }
        }
    }
}
