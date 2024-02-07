package com.example.detectaccessibilityservice

import kotlin.math.pow
import kotlin.math.roundToInt


fun main() {

    val doubleA = 29.13467
    val round = doubleA.roundTo(2)
    println(round)


}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}

fun roundTheNumber(numInDouble: Double): String {
    return "%.2f".format(numInDouble)
}


