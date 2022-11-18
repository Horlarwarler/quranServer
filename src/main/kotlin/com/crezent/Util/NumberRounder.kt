package com.crezent.Util

fun Double.roundUp(roundUpTo: Int = 1): Double{
    val numberToText = "$this"
    val decimalIndex = numberToText.indexOf(".")
    val endIndex = decimalIndex + roundUpTo + 1
    //1.2343
    val firstThree = numberToText.substring(0,endIndex)
    return  firstThree.toDouble()
}