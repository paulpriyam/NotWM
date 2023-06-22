package com.example.forgetnoting.util

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun Long.toDateTimeString(): String {
    val date = Date(this)
    val outputFormat = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.ENGLISH)
    return outputFormat.format(date)
}

fun toDp(context: Context, dp: Float): Int {
    val resources = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
    ).roundToInt()
}