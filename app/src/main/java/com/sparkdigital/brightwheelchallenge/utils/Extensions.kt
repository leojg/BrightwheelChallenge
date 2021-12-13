package com.sparkdigital.brightwheelchallenge.utils

fun Int.formatNumber(): String {
    return if (this >= 1000) {
        val num = (this / 1000f)
        return "%.1fk".format(num)
    } else this.toString()
}