package com.test.testapp.ext

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

private const val METERS_IN_KM = 1_000F

private val distanceFormat = DecimalFormat(".0").apply {
    roundingMode = RoundingMode.DOWN
}

fun Float.metersToKmShort(): String {
    if (this == -1F) {
        return ""
    }
    if (this < METERS_IN_KM) {
        return "${this.roundToInt()} m"
    }
    val km = this / METERS_IN_KM
    return "${distanceFormat.format(km)} km"
}
