package com.development.kc.kiguchiicongenerator

data class PartsDTO(
        //Group_Parts_PartsID_tint/line
        val resString: String,
        val tintColor: Int?,
        val lineColor: Int?,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
)