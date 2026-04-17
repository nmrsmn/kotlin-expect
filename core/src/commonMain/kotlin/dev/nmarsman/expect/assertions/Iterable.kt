package dev.nmarsman.expect.assertions

import dev.nmarsman.expect.api.Assertion

fun <T : Iterable<E>, E> Assertion.Builder<T>.contains(vararg elements: E): Assertion.Builder<T> =
    contains(elements.toList())

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
