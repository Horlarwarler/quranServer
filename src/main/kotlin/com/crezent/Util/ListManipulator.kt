package com.crezent.Util

import com.crezent.domain.model.AyahModel

fun List<AyahModel>.findAyah(uniqueId:Int): AyahModel{
    val  quranModel  = this.find{
        it._id == uniqueId
    }!!
    return quranModel



}
fun List<AyahModel>.updateQuran(ayahModel:AyahModel): List<AyahModel>{
    val newQuranValues: MutableList<AyahModel> = this.toMutableList()
    val index = ayahModel._id -1
    newQuranValues.remove(ayahModel)
    newQuranValues.add(index,ayahModel)
    return newQuranValues.toList()

}