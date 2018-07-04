package com.development.kc.kiguchiicongenerator

data class IconDTO(
        //tint,line2リソースで１組なのでResStrで管理
        val backHairResStr: String = "",
        val bodyResStr: String = "",
        val faceResStr: String = "",
        val eyeResStr: String = "",
        val mouthResStr: String = "",
        val bangResStr: String = "",
        val hairAccResStr: String = "",
        val faceAccResStr: String = ""

//        val hairBID: Int = 0,
//        val bodyID: Int = 0,
//        val faceID: Int = 0,
//        val eyeID: Int = 0,
//        val mouthID: Int = 0,
//        val bangID: Int = 0,
//        val hairAccID: Int = 0,
//        val faceAccID: Int = 0
){
    //全てのレイヤーのDrawableResStrをパラメータとして保持
    //レイヤーが増えたときここも変える必要あり……

}