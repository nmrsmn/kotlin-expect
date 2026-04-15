package dev.nmarsman.expect

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows
import dev.nmarsman.expect.assertions.isEqualTo
import dev.nmarsman.expect.exception.AssertionFailedException

val ComposeTest by testSuite(
    displayName = "Composed assertion tests",
) {
    test(name = "require all should pass when all composed assertions pass") {
        expectThat(10)
            .compose("is a valid number") {
                assertThat("is positive") { it > 0 }
                assertThat("is less than 100") { it < 100 }
            } require { all }
    }

    test(name = "require all should fail when one composed assertion fails") {
        expectThrows<AssertionFailedException> {
            expectThat(150)
                .compose("is a valid number") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100") { it < 100 }
                } require { all }
        }
    }

    test(name = "require all should fail when all composed assertions fail") {
        expectThrows<AssertionFailedException> {
            expectThat(-1)
                .compose("is a valid number") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100 and greater than 50") { it in 51..99 }
                } require { all }
        }
    }

    test(name = "require any should pass when at least one composed assertion passes") {
        expectThat(150)
            .compose("is a notable number") {
                assertThat("is positive") { it > 0 }
                assertThat("is less than 100") { it < 100 }
            } require { any }
    }

    test(name = "require any should fail when all composed assertions fail") {
        expectThrows<AssertionFailedException> {
            expectThat(-1)
                .compose("is a notable number") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is even") { it % 2 == 0 }
                } require { any }
        }
    }

    test(name = "require none should pass when all composed assertions fail") {
        expectThat(-3)
            .compose("is not a standard value") {
                assertThat("is positive") { it > 0 }
                assertThat("is even") { it % 2 == 0 }
            } require { none }
    }

    test(name = "require none should fail when at least one composed assertion passes") {
        expectThrows<AssertionFailedException> {
            expectThat(4)
                .compose("is not a standard value") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is negative") { it < 0 }
                } require { none }
        }
    }

    test(name = "then should allow custom pass logic") {
        expectThat(10)
            .compose("custom check") {
                assertThat("is positive") { it > 0 }
                assertThat("is less than 100") { it < 100 }
            }
            .then {
                if (all) pass() else fail()
            }
    }

    test(name = "then should allow failing based on custom logic") {
        expectThrows<AssertionFailedException> {
            expectThat(10)
                .compose("custom check") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100") { it < 100 }
                }
                .then {
                    if (none) pass() else fail()
                }
        }
    }

    test(name = "then should provide correct passed and failed counts") {
        expectThat(10)
            .compose("counting check") {
                assertThat("is positive") { it > 0 }
                assertThat("is even") { it % 2 == 0 }
                assertThat("is greater than 100") { it > 100 }
            }
            .then {
                if (passedCount == 2 && failedCount == 1) pass() else fail()
            }
    }

    test(name = "compose with expected value should pass when all assertions pass") {
        expectThat(42)
            .compose("is the answer to everything", 42) {
                isEqualTo(42)
            } require { all }
    }

    test(name = "compose should return a builder that allows further chaining") {
        expectThat(10)
            .compose("is a valid number") {
                assertThat("is positive") { it > 0 }
            }
            .require { all }
            .isEqualTo(10)
    }

    test(name = "compose should work with soft assertions when all pass") {
        expectThat(10) {
            compose("is valid") {
                assertThat("is positive") { it > 0 }
                assertThat("is less than 100") { it < 100 }
            } require { all }
        }
    }

    test(name = "compose failure should be collected in soft assertions") {
        expectThrows<AssertionFailedException> {
            expectThat(150) {
                compose("is valid") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100") { it < 100 }
                } require { all }
            }
        }
    }

    test(name = "compose failure should be collected alongside other failures in soft assertions") {
        expectThrows<AssertionFailedException> {
            expectThat(150) {
                isEqualTo(10)
                compose("is valid") {
                    assertThat("is positive") { it > 0 }
                    assertThat("is less than 100") { it < 100 }
                } require { all }
            }
        }
    }

    test(name = "require all should pass when compose block is empty") {
        expectThat(10)
            .compose("vacuously true") {
                // no inner assertions
            } require { all }
    }

    test(name = "require any should fail when compose block is empty") {
        expectThrows<AssertionFailedException> {
            expectThat(10)
                .compose("vacuously false") {
                    // no inner assertions — any with no results is false
                } require { any }
        }
    }

    test(name = "require none should pass when compose block is empty") {
        expectThat(10)
            .compose("vacuously true") {
                // no inner assertions — none with no results is true
            } require { none }
    }
}
