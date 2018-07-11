package com.development.kc.kiguchiicongenerator

import android.graphics.Color

data class IconDTO(
        //tint,line2リソースで１組なのでResStrで管理
//        val backHairResStr: String = "",
//        val bodyResStr: String = "",
//        val faceResStr: String = "",
//        val eyeResStr: String = "",
//        val mouthResStr: String = "",
//        val bangResStr: String = "",
//        val hairAccResStr: String = "",
//        val faceAccResStr: String = ""

        var backHairID: Int = 0,
        var bodyID: Int = 0,
        var faceID: Int = 0,
        var eyeID: Int = 0,
        var mouthID: Int = 0,
        var bangID: Int = 0,
        var hairAccID: Int = 0,
        var faceAccID: Int = 0,
        var backGroundId: Int = 0, //ColorまたはDrawableID

        //hairColor: back/bang共通
        var hairTintColor: Int = 0,
        var hairLineColor: Int = 0,
        var bodyTintColor: Int = 0,
        var bodyLineColor: Int = 0,
        var faceTintColor: Int = 0,
        var faceLineColor: Int = 0,
        var eyeTintColor: Int = 0,
        var eyeLineColor: Int = 0,
        var mouthTintColor: Int = 0,
        var mouthLineColor: Int = 0,
        var hairAccTintColor: Int = 0,
        var hairAccLineColor: Int = 0,
        var faceAccTintColor: Int = 0,
        var faceAccLineColor: Int = 0

)