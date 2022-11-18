package com.crezent.Util

import com.crezent.domain.model.AyahModel

fun List<AyahModel>.findAyah(uniqueId:Int): AyahModel{
    val  quranModel  = this.find{
        it._id == uniqueId
    }!!
    return quranModel



}
fun List<AyahModel>.updateQuran(updatedQuran:List<AyahModel>): List<AyahModel>{
    val newQuranValues: MutableList<AyahModel> = this.toMutableList()
    val quranToUpdate = mutableListOf<AyahModel>()
    updatedQuran.forEach {
        val ayahToUpdate = this.findAyah(it._id)
        quranToUpdate.add(ayahToUpdate)
    }
    newQuranValues.removeAll(quranToUpdate)
    updatedQuran.forEach {
        val index = it._id -1
        newQuranValues.add(index,it )
    }
    return newQuranValues.toList()

}