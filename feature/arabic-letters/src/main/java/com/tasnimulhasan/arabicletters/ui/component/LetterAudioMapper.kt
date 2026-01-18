package com.tasnimulhasan.arabicletters.ui.component

import com.tasnimulhasan.designsystem.R as Res

object LetterAudioMapper {
    fun isolated(isolatedForm: String): Int = when (isolatedForm) {
        "ا" -> Res.raw.alif
        "ب" -> Res.raw.baa
        "ت" -> Res.raw.taa
        "ث" -> Res.raw.thaa
        "ج" -> Res.raw.jeem
        "ح" -> Res.raw.haa
        "خ" -> Res.raw.khaa
        "د" -> Res.raw.daal
        "ذ" -> Res.raw.zaal
        "ر" -> Res.raw.raa
        "ز" -> Res.raw.zaa
        "س" -> Res.raw.seen
        "ش" -> Res.raw.sheen
        "ص" -> Res.raw.saad
        "ض" -> Res.raw.daad
        "ط" -> Res.raw.taah
        "ظ" -> Res.raw.zhaa
        "ع" -> Res.raw.ain
        "غ" -> Res.raw.ghain
        "ف" -> Res.raw.faa
        "ق" -> Res.raw.qaaf
        "ك" -> Res.raw.kaaf
        "ل" -> Res.raw.laam
        "م" -> Res.raw.meem
        "ن" -> Res.raw.noon
        "ه" -> Res.raw.haah
        "و" -> Res.raw.waw
        "ي" -> Res.raw.yaa
        "ء" -> Res.raw.hamzah
        "لا" -> Res.raw.laaa
        else -> Res.raw.alif
    }
}