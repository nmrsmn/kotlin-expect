package dev.nmarsman.expect.exception

import com.willowtreeapps.opentest4k.AssertionFailedError

class AssertionFailedException : AssertionFailedError {
    constructor(message: String, expected: Any?, actual: Any?, cause: Throwable?) :
        super(message, expected, actual, cause)

    constructor(message: String, cause: Throwable?) : super(message, cause)
}
