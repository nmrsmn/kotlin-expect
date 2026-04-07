package dev.nmarsman.expect.internal

import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

internal fun <Receiver, Result> (Receiver.() -> Result).describe(): String =
    when (this) {
        is KProperty<*> -> "value of the property $name ({})"
        is KFunction<*> -> "return value of $name ({})"
        else -> "<anonymous lambda> ({})"
    }
