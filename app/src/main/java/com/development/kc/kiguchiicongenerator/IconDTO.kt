package com.development.kc.kiguchiicongenerator

import android.graphics.Color
import java.io.Serializable

class IconDTO(private val tag: String?): Serializable{
    constructor(): this(null)

    private val partsMap = mutableMapOf<IconLayout.GroupEnum, Int>().also {
        for (v in IconLayout.GroupEnum.values()){
            it[v] = 1
        }
    }

    private val lineColorMap = mutableMapOf<IconLayout.GroupEnum, Int>().also {
        for (v in IconLayout.GroupEnum.values()){
            it[v] = when(v){
                IconLayout.GroupEnum.BACK_HAIR -> Color.DKGRAY
                IconLayout.GroupEnum.BODY -> Color.DKGRAY
                IconLayout.GroupEnum.FACE -> Color.DKGRAY
                IconLayout.GroupEnum.EYE -> Color.DKGRAY
                IconLayout.GroupEnum.MOUTH -> Color.DKGRAY
                IconLayout.GroupEnum.BANG -> Color.DKGRAY
                IconLayout.GroupEnum.HAIR_ACC -> Color.DKGRAY
                IconLayout.GroupEnum.FACE_ACC -> Color.DKGRAY
                IconLayout.GroupEnum.COMMENT -> Color.DKGRAY
                else -> throw IllegalArgumentException("レイヤーを追加してください")
            }
        }
    }

    private val tintColorMap = mutableMapOf<IconLayout.GroupEnum, Int>().also {
        for (v in IconLayout.GroupEnum.values()){
            it[v] = when(v){
                IconLayout.GroupEnum.BACK_HAIR -> Color.parseColor("#ff888888")
                IconLayout.GroupEnum.BODY -> Color.parseColor("#fce2c4")
                IconLayout.GroupEnum.FACE -> Color.parseColor("#fce2c4")
                IconLayout.GroupEnum.EYE -> Color.parseColor("#ff444444")
                IconLayout.GroupEnum.MOUTH -> Color.parseColor("#ffdf7163")
                IconLayout.GroupEnum.BANG -> Color.parseColor("#ff888888")
                IconLayout.GroupEnum.HAIR_ACC -> Color.parseColor("#e9dfe5")
                IconLayout.GroupEnum.FACE_ACC -> Color.parseColor("#e9dfe5")
                IconLayout.GroupEnum.COMMENT -> Color.DKGRAY
                else -> throw IllegalArgumentException("レイヤーを追加してください")
            }
        }
    }

    private var backGroundResId: Int? = null

    fun setPartsId(group: IconLayout.GroupEnum, partsId: Int){
        partsMap[group] = partsId
    }

    fun setColorFilter(group: IconLayout.GroupEnum, baseTypeEnum: IconLayout.BaseTypeEnum, color: Int){
        val map = when(baseTypeEnum){
            IconLayout.BaseTypeEnum.TINT -> tintColorMap
            IconLayout.BaseTypeEnum.LINE -> lineColorMap
        }

        if (group == IconLayout.GroupEnum.BANG || group == IconLayout.GroupEnum.BACK_HAIR){
            map[IconLayout.GroupEnum.BANG] = color
            map[IconLayout.GroupEnum.BACK_HAIR] = color
        } else {
            map[group] = color
        }
    }

    fun getPartsId(group: IconLayout.GroupEnum): Int{
        return partsMap[group]!!
    }

    fun getColorFilter(group: IconLayout.GroupEnum, baseTypeEnum: IconLayout.BaseTypeEnum): Int {
        val map = when(baseTypeEnum){
            IconLayout.BaseTypeEnum.TINT -> tintColorMap
            IconLayout.BaseTypeEnum.LINE -> lineColorMap
        }
        return map[group]!!
    }

    fun getTag(): String?{
        return tag
    }

    fun setBackground(resId: Int){

    }
}