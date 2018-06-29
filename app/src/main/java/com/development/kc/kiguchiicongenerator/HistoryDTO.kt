package com.development.kc.kiguchiicongenerator

import java.util.*

data class HistoryDTO(
        val iconDTO: IconDTO, //アイコンセット
        val frameType: Int, //枠形状
        val date: Date //保存日付

)