package com.development.kc.kiguchiicongenerator

data class IconDTO(
        val hairBID: Int = 0,
        val bodyID: Int = 0,
        val faceID: Int = 0,
        val eyeID: Int = 0,
        val mouthID: Int = 0,
        val bangID: Int = 0,
        val hairAccID: Int = 0,
        val faceAccID: Int = 0 ){
    //全てのレイヤーのDrawableIDをパラメータとして保持
    //レイヤーが増えたときここも変える必要あり……

}