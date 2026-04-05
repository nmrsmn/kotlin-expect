package dev.nmarsman.expect.internal

/**
 * Wraps the subject of an assertion with an optional description.
 *
 * When [description] is not null, it is used to describe the subject in
 * failure messages instead of the formatted representation of [subject].
 *
 * @param T The type of the subject.
 * @property subject The actual subject value.
 * @property description An optional description to use when formatting the subject.
 */
internal data class AssertionSubject<T>(
    val subject: T,
    val description: String? = null,
)
