package dev.nmarsman.expect.internal

import kotlin.reflect.KClass

@Suppress("TooManyFunctions")
internal object AssertionFailedMessageFormatter {
    private const val INDENT = "   "
    private const val FORMATTED_VALUE_MAX_LENGTH = 40

    fun format(
        context: AssertionGroup<*>,
    ): String = buildString {
        with(IndentedStringBuilderScope(builder = this, indent = "")) {
            with(context.root) {
                formatAssertionGroup()
            }
        }
    }.trimEnd()

    context(context: AssertionGroup<*>)
    private fun IndentedStringBuilderScope.formatAssertionGroup() {
        appendLine(context.describe())

        withIndent {
            format(children = context.children)
        }
    }

    private fun IndentedStringBuilderScope.format(children: Iterable<AssertionNode<*>>) = children.forEach { node ->
        with(node) {
            formatNode()
            appendLine()
        }
    }

    context(context: AssertionNode<*>)
    private fun IndentedStringBuilderScope.formatNode() =
        when (context) {
            is AssertionGroup<*> -> formatAssertionGroup()
            is AssertionResult<*> -> formatAssertionResult()
        }

    context(context: AssertionResult<*>)
    private fun IndentedStringBuilderScope.formatAssertionResult() =
        withIndent(indent = "") {
            appendLine(context.describe())

            when (val status = context.status) {
                is AssertionResult.Status.Failed if status.description != null ->
                    withIndent(indent = "  ") {
                        appendLine(status.description.replaceWith(status.actual))
                    }

                else -> Unit
            }
        }

    internal fun AssertionNode<*>.describe(): String =
        JoinedStringBuilderScope(StringBuilder(), joinBy = " ").apply {
            when (this@describe) {
                is AssertionSubject<*> -> append("▼")
                is AssertionResult<*> -> append(status.symbol)
            }

            when (this@describe) {
                is AssertionSubject<*> if root == this@describe -> append("Expect that")
                else -> Unit
            }

            val description = description ?: "{}"

            val appendix = when (this@describe) {
                is AssertionGroup<*> -> ":"
                else -> ""
            }

            val replacement = when (this@describe) {
                is AssertionResult<*> -> expected
                else -> subject
            }

            append("${description.replaceWith(replacement)}${appendix}")
        }.toString()

    private fun <T> String.replaceWith(subject: T) =
        when (contains("{}")) {
            true -> replace(
                oldValue = "{}",
                newValue = formatValue(subject).toString(),
            )

            else -> this
        }

    internal fun formatValue(value: Any?): Any? =
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

    private fun Appendable.withIndent(indent: String = INDENT, block: IndentedStringBuilderScope.() -> Unit) {
        IndentedStringBuilderScope(this, indent).block()
    }

    private class IndentedStringBuilderScope(
        val builder: Appendable,
        private val indent: String = INDENT,
    ) : Appendable by builder {
        override fun append(value: CharSequence?): Appendable =
            builder.append("$indent$value")

        fun appendLine(value: CharSequence?): Appendable =
            builder.appendLine("$indent$value")
    }

    private class JoinedStringBuilderScope(
        private val builder: StringBuilder,
        val joinBy: String = " ",
    ) : Appendable by builder {
        override fun append(value: CharSequence?): Appendable =
            when (builder.isEmpty()) {
                false -> builder.append(joinBy).append(value)
                true -> builder.append(value)
            }

        override fun toString(): String = builder.toString()
    }
}
