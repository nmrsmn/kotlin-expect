package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows

val CharSequenceTest by testSuite(
    displayName = "Char sequence assertion tests",
) {
    testSuite(name = "`isNullOrEmpty` assertion") {
        test(name = "Passes when the subject is `null`") {
            expectThat(null)
                .isNullOrEmpty()
        }

        test(name = "Passes when the subject is ''") {
            expectThat("")
                .isNullOrEmpty()
        }

        listOf("subject", " ", "\r\n").forEach {
            test(name = "Fails when the subject is '$it'") {
                expectThrows<AssertionError> {
                    expectThat(it)
                        .isNullOrEmpty()
                }
            }
        }
    }

    testSuite(name = "`isEmpty` assertion") {
        test(name = "Passes when the subject is ''") {
            expectThat("")
                .isEmpty()
        }

        listOf("subject", " ", "\r\n").forEach {
            test(name = "Fails when the subject is '$it'") {
                expectThrows<AssertionError> {
                    expectThat(it)
                        .isEmpty()
                }
            }
        }
    }

    testSuite(name = "`isNotEmpty` assertion") {
        listOf("subject", " ", "\r\n").forEach {
            test(name = "Passes when the subject is '$it'") {
                expectThat(it)
                    .isNotEmpty()
            }
        }

        test(name = "Fails when the subject is ''") {
            expectThrows<AssertionError> {
                expectThat("")
                    .isNotEmpty()
            }
        }
    }

    testSuite(name = "`isNullOrBlank` assertion") {
        test(name = "Passes when the subject is `null`") {
            expectThat(null)
                .isNullOrBlank()
        }

        listOf("", " ", "\r\n").forEach {
            test(name = "Passes when the subject is '$it'") {
                expectThat(it)
                    .isNullOrBlank()
            }
        }

        listOf("subject", " a", "[]").forEach {
            test(name = "Fails when the subject is '$it'") {
                expectThrows<AssertionError> {
                    expectThat(it)
                        .isNullOrBlank()
                }
            }
        }
    }

    testSuite(name = "`isBlank` assertion") {
        listOf("", " ", "\r\n").forEach {
            test(name = "Passes when the subject is '$it'") {
                expectThat(it)
                    .isBlank()
            }
        }

        listOf("subject", " a", "[]").forEach {
            test(name = "Fails when the subject is '$it'") {
                expectThrows<AssertionError> {
                    expectThat(it)
                        .isBlank()
                }
            }
        }
    }

    testSuite(name = "`isNotBlank` assertion") {
        listOf("subject", " a", "[]").forEach {
            test(name = "Passes when the subject is '$it'") {
                expectThat(it)
                    .isNotBlank()
            }
        }

        listOf("", " ", "\r\n").forEach {
            test(name = "Fails when the subject is \'$it\'") {
                expectThrows<AssertionError> {
                    expectThat(it)
                        .isNotBlank()
                }
            }
        }
    }

    testSuite(name = "`hasLength` assertion") {
        test(name = "Passes when the subject has the expected length") {
            expectThat("subject")
                .hasLength(7)
        }

        test(name = "Fails when the subject does not have the expected length") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .hasLength(1)
            }
        }
    }

    testSuite(name = "`startsWith` assertion") {
        test(name = "Passes when the subject starts with the expected character") {
            expectThat("subject")
                .startsWith('s')
        }

        test(name = "Passes when the subject starts with the expected characters") {
            expectThat("subject")
                .startsWith("sub")
        }

        test(name = "Fails when the subject does not have start with the expected character") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .startsWith('x')
            }
        }

        test(name = "Fails when the subject does not have start with the expected characters") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .startsWith("ject")
            }
        }
    }

    testSuite(name = "`endsWith` assertion") {
        test(name = "Passes when the subject ends with the expected character") {
            expectThat("subject")
                .endsWith('t')
        }

        test(name = "Passes when the subject ends with the expected characters") {
            expectThat("subject")
                .endsWith("ect")
        }

        test(name = "Fails when the subject does not have end with the expected character") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .endsWith('x')
            }
        }

        test(name = "Fails when the subject does not have end with the expected characters") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .endsWith("sub")
            }
        }
    }

    testSuite(name = "`matches` assertion") {
        test(name = "Passes when the subject is a full match for the regex") {
            expectThat("subject")
                .matches("[subject]+".toRegex())
        }

        test(name = "Fails when the subject is only a partial match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .matches("[ubj]+".toRegex())
            }
        }

        test(name = "Fails when the subject is a case insensitive match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .matches("[SUBJECT]+".toRegex())
            }
        }

        test(name = "Fails when the subject does not match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .matches("\\d+".toRegex())
            }
        }
    }

    testSuite(name = "`matchesIgnoringCase` assertion") {
        test(name = "Passes when the subject is a full match for the regex") {
            expectThat("subject")
                .matchesIgnoringCase("[subject]+".toRegex())
        }

        test(name = "Fails when the subject is only a partial match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .matchesIgnoringCase("[ubj]+".toRegex())
            }
        }

        test(name = "Passes when the subject is a case insensitive match for the regex") {
            expectThat("subject")
                .matchesIgnoringCase("[SUBJECT]+".toRegex())
        }

        test(name = "Fails when the subject does not match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .matchesIgnoringCase("\\d+".toRegex())
            }
        }
    }

    testSuite(name = "`contains(Regex)` assertion") {
        test(name = "Passes when the subject is a full match for the regex") {
            expectThat("subject")
                .contains("[subject]+".toRegex())
        }

        test(name = "Passes when the subject is only a partial match for the regex") {
            expectThat("subject")
                .contains("[ubj]+".toRegex())
        }

        test(name = "Fails when the subject is a case insensitive match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .contains("[SUBJECT]+".toRegex())
            }
        }

        test(name = "Fails when the subject does not match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .contains("\\d+".toRegex())
            }
        }
    }

    testSuite(name = "`containsIgnoringCase(Regex)` assertion") {
        test(name = "Passes when the subject is a full match for the regex") {
            expectThat("subject")
                .containsIgnoringCase("[subject]+".toRegex())
        }

        test(name = "Passes when the subject is only a partial match for the regex") {
            expectThat("subject")
                .containsIgnoringCase("[ubj]+".toRegex())
        }

        test(name = "Fails when the subject is a case insensitive match for the regex") {
            expectThat("subject")
                .containsIgnoringCase("[SUBJECT]+".toRegex())
        }

        test(name = "Fails when the subject does not match for the regex") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .containsIgnoringCase("\\d+".toRegex())
            }
        }
    }

    testSuite(name = "`contains(CharSequence)` assertion") {
        test(name = "Passes when the subject contains the expected substring") {
            expectThat("subject")
                .contains("ubj")
        }

        test(name = "Fails when the subject contains the expected substring in a different case") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .contains("UBJ")
            }
        }

        test(name = "Fails when the subject does not contain the expected substring") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .contains("different")
            }
        }
    }

    testSuite(name = "`containsIgnoringCase(Regex)` assertion") {
        test(name = "Passes when the subject contains the expected substring") {
            expectThat("subject")
                .containsIgnoringCase("ubj")
        }

        test(name = "Passes when the subject contains the expected substring in a different case") {
            expectThat("subject")
                .containsIgnoringCase("UBJ")
        }

        test(name = "Fails when the subject does not contain the expected substring") {
            expectThrows<AssertionError> {
                expectThat("subject")
                    .containsIgnoringCase("different")
            }
        }
    }
}
