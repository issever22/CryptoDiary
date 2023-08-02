package com.issever.cryptodiary.util.extensions

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.regex.Pattern

fun String?.emptyIfNull(): String {
    return when {
        this.isNullOrEmpty() -> ""
        else -> this
    }
}

fun String?.isValidEmailAddress(): Boolean {
    val regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this.toString())
    return matcher.matches()
}

fun String?.trimBrackets(): String {
    return this?.replace(Regex("[()]"), "") ?: ""
}

fun String.capitalizeFirstLetter(): String {
    return this.substring(0, 1).uppercase() + this.substring(1)
}

fun String.convertToSystemDateFormat(): String {
    val sourceFormats = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy")
    )

    var date: LocalDate? = null
    for (sourceFormat in sourceFormats) {
        try {
            date = LocalDate.parse(this, sourceFormat)
            break
        } catch (e: DateTimeParseException) {
            continue
        }
    }

    if (date == null) {
        return this
    }

    val targetFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, Locale.getDefault())
    val utilDate = java.util.Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())
    return targetFormat.format(utilDate)
}

