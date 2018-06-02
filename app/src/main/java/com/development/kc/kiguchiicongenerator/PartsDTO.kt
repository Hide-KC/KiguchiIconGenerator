package com.development.kc.kiguchiicongenerator

data class PartsDTO(
        val partsResourceString: String,
        val backColor: Int?,
        val lineColor: Int?,
        val partsShiftX: Float = 0f,
        val partsShiftY: Float = 0f
)