@file:Suppress("TooManyFunctions")

package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

/**
 * Asserts that all elements of the subject pass the assertions in [predicate].
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.all(predicate: Assertion.Builder<E>.() -> Unit): Assertion.Builder<T> =
    compose(
        description = "all elements match",
    ) {
        subject.forEach { element ->
            get(
                description = "{}",
                function = { element },
            ).apply(predicate)
        }
    } require { all }

/**
 * Asserts that at least one element of the subject pass the assertions in [predicate].
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.any(predicate: Assertion.Builder<E>.() -> Unit): Assertion.Builder<T> =
    compose(
        description = "at least one element matches",
    ) {
        subject.forEach { element ->
            get(
                description = "{}",
                function = { element },
            ).apply(predicate)
        }
    } require { any }

/**
 * Asserts that no elements of the subject pass the assertions in [predicate].
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.none(predicate: Assertion.Builder<E>.() -> Unit): Assertion.Builder<T> =
    compose(
        description = "none of the elements match",
    ) {
        subject.forEach { element ->
            get(
                description = "{}",
                function = { element },
            ).apply(predicate)
        }
    } require { none }

/**
 * Asserts that all of the [elements] are present in the subject.
 * The elements may be in any order, any number of times, and the subject may contain other elements as specified.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.contains(vararg elements: E): Assertion.Builder<T> =
    contains(elements.toList())

/**
 * Asserts that all of the [elements] are present in the subject.
 * The elements may be in any order, any number of times, and the subject may contain other elements as specified.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.contains(elements: Collection<E>): Assertion.Builder<T> =
    when {
        elements.isEmpty() ->
            assert(
                description = "contains the elements {}",
                expected = elements,
            ) {
                pass()
            }

        elements.size == 1 ->
            assert(
                description = "contains the elements {}",
                expected = elements,
            ) {
                if (subject.contains(elements.first())) {
                    pass()
                } else {
                    fail()
                }
            }

        else ->
            compose(
                description = "contains the elements {}",
                expected = elements,
            ) {
                elements.forEach { element ->
                    assert(
                        description = "contains {}",
                        expected = element,
                    ) {
                        if (subject.contains(element)) {
                            pass()
                        } else {
                            fail()
                        }
                    }
                }
            } require { all }
    }

/**
 * Asserts that none of the [elements] are present in the subject.
 * Whenever [elements] is empty the assertion always fails.
 * If the subject is empty the assertion passes if no [elements] are present in the subject.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.doesNotContain(vararg elements: E): Assertion.Builder<T> =
    doesNotContain(elements.toList())

/**
 * Asserts that none of the [elements] are present in the subject.
 * Whenever [elements] is empty the assertion always fails.
 * If the subject is empty the assertion passes if no [elements] are present in the subject.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.doesNotContain(elements: Collection<E>): Assertion.Builder<T> =
    when {
        elements.isEmpty() ->
            throw IllegalArgumentException("You must supply some expected elements.")

        elements.size == 1 ->
            assert(
                description = "does not contain {}",
                expected = elements,
            ) {
                if (subject.contains(elements.first())) {
                    fail()
                } else {
                    pass()
                }
            }

        else ->
            compose(
                description = "does not contain any of the elements {}",
                expected = elements,
            ) {
                elements.forEach { element ->
                    assert(
                        description = "does not contain {}",
                        expected = element,
                    ) {
                        if (subject.contains(element)) {
                            fail()
                        } else {
                            pass()
                        }
                    }
                }
            } require { all }
    }

/**
 * Asserts that all of the [elements], and no others, are present in the subject in the specified order.
 * Whenever the subject has no guaranteed iteration order (like [Set]) this assertion is probably
 * not appropriate and you should [containsExactlyInAnyOrder] instead.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.containsExactly(vararg elements: E): Assertion.Builder<T> =
    containsExactly(elements.toList())

/**
 * Asserts that all of the [elements], and no others, are present in the subject in the specified order.
 * Whenever the subject has no guaranteed iteration order (like [Set]) this assertion is probably
 * not appropriate and you should [containsExactlyInAnyOrder] instead.
 */
@Suppress("CognitiveComplexMethod")
fun <T : Iterable<E>, E> Assertion.Builder<T>.containsExactly(elements: Collection<E>): Assertion.Builder<T> =
    compose(
        description = "contains exactly the elements {}",
        expected = elements,
    ) {
        val original = subject.toList()
        val remaining = subject.toMutableList()

        elements.forEachIndexed { index, element ->
            compose(
                description = "…at index {}",
                expected = index,
            ) {
                assert(
                    description = "contains {}",
                    expected = element,
                ) {
                    if (remaining.remove(element)) {
                        when {
                            index >= original.size ->
                                fail()

                            original[index] === element ->
                                pass()

                            else ->
                                fail(actual = original[index])
                        }
                    } else {
                        fail()
                    }
                }
            } require { all }
        }

        assert(
            description = "contains no further elements {}",
            expected = emptyList<E>(),
        ) {
            if (remaining.isEmpty()) {
                pass()
            } else {
                fail(actual = remaining.toList())
            }
        }
    } require { all }

/**
 * Asserts that all of the [elements], and no others, are present in the subject.
 * Order is not being evaluated in this assertion.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.containsExactlyInAnyOrder(vararg elements: E): Assertion.Builder<T> =
    containsExactlyInAnyOrder(elements.toList())

/**
 * Asserts that all of the [elements], and no others, are present in the subject.
 * Order is not being evaluated in this assertion.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.containsExactlyInAnyOrder(elements: Collection<E>): Assertion.Builder<T> =
    compose(
        description = "contains exactly the elements {} in any order",
        expected = elements,
    ) {
        val remaining = subject.toMutableList()

        elements.forEach { element ->
            assert(
                description = "contains {}",
                expected = element,
            ) {
                if (remaining.remove(element)) {
                    pass()
                } else {
                    fail()
                }
            }
        }

        assert(
            description = "contains no further elements {}",
            expected = emptyList<E>(),
        ) {
            if (remaining.isEmpty()) {
                pass()
            } else {
                fail(actual = remaining.toList())
            }
        }
    } require { all }

/**
 * Asserts that the subject iterable is sorted according to the Comparator.
 */
fun <T : Iterable<E>, E> Assertion.Builder<T>.isSorted(comparator: Comparator<E>): Assertion.Builder<T> =
    assert(
        description = "is sorted",
    ) {
        val violation = subject
            .zipWithNext()
            .firstOrNull { (first, second) ->
                comparator.compare(first, second) > 0
            }

        if (violation == null) {
            pass()
        } else {
            fail(
                actual = violation,
                description = "but {0} is greater than {1}",
            )
        }
    }

/**
 * Asserts that the subject iterable is sorted according to natural order.
 */
fun <T : Iterable<E>, E : Comparable<E>> Assertion.Builder<T>.isSorted(): Assertion.Builder<T> =
    isSorted(comparator = naturalOrder())
