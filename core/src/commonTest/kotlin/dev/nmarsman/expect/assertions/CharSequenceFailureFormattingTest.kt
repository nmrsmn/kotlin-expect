package dev.nmarsman.expect.assertions

import de.infix.testBalloon.framework.core.testSuite
import dev.nmarsman.expect.api.expectThat
import dev.nmarsman.expect.api.expectThrows

val CharSequenceFailureFormattingTest by testSuite(
    displayName = "Char sequence failure formatting tests",
) {
    test(name = "`isNullOrEmpty` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .isNullOrEmpty()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is null or empty")
        }
    }

    test(name = "`isEmpty` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .isEmpty()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is empty")
        }
    }

    test(name = "`isNotEmpty` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("")
                .isNotEmpty()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is not empty")
        }
    }

    test(name = "`isNullOrBlank` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .isNullOrBlank()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is null or blank")
        }
    }

    test(name = "`isBlank` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .isBlank()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is blank")
        }
    }

    test(name = "`isNotBlank` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("")
                .isNotBlank()
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ is not blank")
        }
    }

    test(name = "`hasLength` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .hasLength(4)
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ has length 4")
                .contains("but was: 7")
        }
    }

    test(name = "`startsWith(Char)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .startsWith('a')
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ starts with 'a'")
                .contains("but started with 's'")
        }
    }

    test(name = "`startsWith(CharSequence)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .startsWith("other")
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ starts with \"other\"")
                .contains("but started with \"subje\"")
        }
    }

    test(name = "`endsWith(Char)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .endsWith('a')
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ ends with 'a'")
                .contains("but ended with 't'")
        }
    }

    test(name = "`endsWith(CharSequence)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .endsWith("other")
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ ends with \"other\"")
                .contains("but ended with \"bject\"")
        }
    }

    test(name = "`matches(Regex)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .matches("\\d+".toRegex())
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ matches the regular expression /\\d+/")
        }
    }

    test(name = "`matchesIgnoringCase(Regex)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .matchesIgnoringCase("\\d+".toRegex())
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ matches the regular expression /\\d+/ (ignoring case)")
        }
    }

    test(name = "`contains(Regex)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .contains("\\d+".toRegex())
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ contains a match for the regular expression /\\d+/")
        }
    }

    test(name = "`containsIgnoringCase(Regex)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .containsIgnoringCase("\\d+".toRegex())
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ contains a match for the regular expression /\\d+/ (ignoring case)")
        }
    }

    test(name = "`contains(CharSequence)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .contains("other")
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ contains \"other\"")
        }
    }

    test(name = "`containsIgnoringCase(CharSequence)` assertion failure is formatted correctly") {
        expectThrows<AssertionError> {
            expectThat("subject")
                .containsIgnoringCase("other")
        }.also {
            it.get(AssertionError::message)
                .isNotNull()
                .contains("✗ contains \"other\" (ignoring case)")
        }
    }
}
