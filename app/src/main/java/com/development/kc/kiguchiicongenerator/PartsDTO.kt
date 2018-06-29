package com.development.kc.kiguchiicongenerator

data class PartsDTO(
        //「Group名_Parts番号_」まで格納
        val partsResString: String,
        val partsResId: Int,
        val tintColor: Int?,
        val lineColor: Int?,
        val partsShiftX: Float = 0f,
        val partsShiftY: Float = 0f
)