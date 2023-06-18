package com.example.forgetnoting.util

import java.text.SimpleDateFormat
import java.util.*


fun Long.toDateTimeString(): String {
    val date = Date(this)
    val outputFormat = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.ENGLISH)
    return outputFormat.format(date)
}