package com.velagissellint.presentation

fun convertDateForUser(dayOfMonth: Int, month: Int, year: Int): String {
    var textDay = "$dayOfMonth."
    if (dayOfMonth < 10)
        textDay = "0$textDay"
    var textMonth = "${month + 1}."
    if (month + 1 < 10)
        textMonth = "0${textMonth}"
    return textDay+textMonth+year
}