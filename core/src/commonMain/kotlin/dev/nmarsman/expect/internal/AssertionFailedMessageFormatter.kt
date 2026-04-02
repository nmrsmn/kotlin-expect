package dev.nmarsman.expect.internal

import dev.nmarsman.expect.internal.AssertionResult.Status

internal object AssertionFailedMessageFormatter {
    fun <T> format(subject: T, results: List<AssertionResult>): String = buildString {
        appendLine("▼ Expect that ${formatSubject(subject)}:")
        results.forEach { result ->
            appendLine(formatResult(result))
        }
    }.trimEnd()

    private fun <T> formatSubject(subject: T): String = when (subject) {
        is String -> "\"$subject\""
        null -> "null"
        else -> subject.toString()
    }

    private fun formatResult(result: AssertionResult): String = buildString {
        append(INDENT)
        append("${result.status.symbol} ${result.description}")

        when (val status = result.status) {
            is Status.Failed -> {
                status.description?.let {
                    appendLine()
                    append("${INDENT.repeat(3)}but was: $it")
                }
            }

            else -> Unit
        }

    }

    private const val INDENT = "   "
}
