package com.development.kc.kiguchiicongenerator

data class PartsDTO(
        //「Group名_Parts番号_」まで格納
        val resString: String,
        val tintColor: Int?,
        val lineColor: Int?,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
)