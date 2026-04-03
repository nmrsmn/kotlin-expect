package dev.nmarsman.expect.internal

import dev.nmarsman.expect.internal.AssertionResult.Status
import kotlin.reflect.KClass

internal object AssertionFailedMessageFormatter {
    private const val INDENT = "   "
    private const val FORMATTED_VALUE_MAX_LENGTH = 40

    fun <T> format(
        subject: T,
        results: List<AssertionResult>,
    ): String = buildString {
        appendLine("▼ Expect that ${formatValue(subject)}:")
        results.forEach { result ->
            appendLine(formatResult(result))
        }
    }.trimEnd()

    private fun formatResult(result: AssertionResult): String = buildString {
        val description = when (result.description.contains("{}")) {
            true -> result.description.replace(
                oldValue = "{}",
                newValue = formatValue(result.expected).toString(),
            )

            false -> result.description
        }

        withIndent {
            appendLine("${result.status.symbol} $description")

            when (val status = result.status) {
                is Status.Failed ->
                    status.description?.let {
                        withIndent(
                            indent = INDENT.repeat(n = 3),
                        ) {
                            appendLine("but was: $it")
                        }
                    }

                else -> Unit
            }
        }
    }

    private fun formatValue(value: Any?): Any? =
        when (value) {
            null -> "null"
            is Number -> formatNumberValue(value)
            is CharSequence -> "\"$value\""
            is Char -> "'$value'"
            is Regex -> "/${value.pattern}/"
            is KClass<*> -> value.simpleName
            else -> formatCompositeValue(value)
        }

    private fun formatNumberValue(value: Number): Any =
        when (value) {
            is Byte -> "0x${value.toString(radix = 16)}"
            else -> value
        }

    private fun formatCompositeValue(value: Any): Any? =
        when (value) {
            is ByteArray ->
                "0x${value.toHexString(format = HexFormat.Default)}"
                    .truncate()

            is Collection<*> ->
                value.toList()
                    .map(::formatValue)

            is Array<*> ->
                formatValue(value.toList())

            is Pair<*, *> ->
                formatValue(value.first) to formatValue(value.second)

            is Map<*, *> ->
                value
                    .mapKeys { formatValue(it.key) }
                    .mapValues { formatValue(it.value) }

            else -> value.toString()
        }

    private fun CharSequence.truncate(maxLength: Int = FORMATTED_VALUE_MAX_LENGTH) =
        when {
            length <= maxLength -> this
            else -> substring(0, maxLength) + "…"
        }

    private fun StringBuilder.withIndent(indent: String = INDENT, block: IndentedStringBuilderScope.() -> Unit) {
        IndentedStringBuilderScope(this, indent).block()
    }

    private class IndentedStringBuilderScope(
        private val builder: StringBuilder,
        val indent: String,
    ) : Appendable by builder {
        override fun append(value: CharSequence?): StringBuilder =
            builder.append(indent).append(value)
    }
}
