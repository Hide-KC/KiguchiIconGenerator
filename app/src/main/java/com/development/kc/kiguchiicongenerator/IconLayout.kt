package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.PorterDuff
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.support.graphics.drawable.VectorDrawableCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView

class IconLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs){
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): this(context, attrs)

    enum class GroupEnum(val groupStr: String) {
        BACK_HAIR("backhair"),
        BODY("body"),
        FACE("face"),
        EYE("eye"),
        MOUTH("mouth"),
        BANG("bang"),
        HAIR_ACC("hairacc"),
        FACE_ACC("faceacc"),
        COMMENT("comment");

        companion object {
            fun getTypeByValue(value: String): GroupEnum{
                for (g in values()){
                    if (g.groupStr == value){
                        return g
                    }
                }
                throw IllegalArgumentException("undefined : $value")
            }
        }
    }

    enum class BaseTypeEnum{
        TINT, LINE
    }

    //レイヤーの階層を定義。深層から表層の順に列挙。中身はカスタムビュー
    private val groupList = listOf(
            PartsBaseLayout(context, GroupEnum.BACK_HAIR),
            PartsBaseLayout(context, GroupEnum.BODY),
            PartsBaseLayout(context, GroupEnum.EYE),
            PartsBaseLayout(context, GroupEnum.MOUTH),
            PartsBaseLayout(context, GroupEnum.BANG),
            PartsBaseLayout(context, GroupEnum.COMMENT)
    )

    init {
        for (i in 0..(groupList.size - 1)){
            this.addView(groupList[i])
            groupList[i].layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    private fun getLayer(tag: GroupEnum): PartsBaseLayout?{
        var layer: PartsBaseLayout? = null
        for (l in groupList){
            if (l.tag == tag){
                layer = l
            }
        }
        return layer
    }

    fun setParts(group: GroupEnum, partsId: Int){
        val layer = getLayer(group)
        if (layer != null){
            val baseTint = layer.findViewById<ImageView>(layer.baseTintId)
            val baseLine = layer.findViewById<ImageView>(layer.baseLineId)

            val builder = StringBuilder()
            builder.append("ic_").append(group.groupStr).append("_").append(String.format("%03d", partsId)).append("_")
            val tintDrawableId = context!!.resources.getIdentifier(builder.toString() + "tint", "drawable", context!!.packageName)
            val lineDrawableId = context!!.resources.getIdentifier(builder.toString() + "line", "drawable", context!!.packageName)

            if (tintDrawableId != 0){
                val tintDrawable = VectorDrawableCompat.create(context!!.resources, tintDrawableId, null)
                baseTint?.setImageDrawable(tintDrawable)
            }

            if (lineDrawableId != 0){
                val lineDrawable = VectorDrawableCompat.create(context!!.resources, lineDrawableId, null)
                baseLine?.setImageDrawable(lineDrawable)
            }
        }
    }

    fun setColorFilter(group: GroupEnum, baseType: BaseTypeEnum, color: Int){
        if (group == GroupEnum.BACK_HAIR || group == GroupEnum.BANG){
            val layerArray = arrayOf(
                    getLayer(GroupEnum.BACK_HAIR), getLayer(GroupEnum.BANG)
            )
            for (layer in layerArray){
                if (layer != null){
                    val base = when(baseType){
                        IconLayout.BaseTypeEnum.TINT -> layer.findViewById<ImageView>(layer.baseTintId)
                        IconLayout.BaseTypeEnum.LINE -> layer.findViewById<ImageView>(layer.baseLineId)
                    }
                    base.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                }
            }
        } else {
            val layer = getLayer(group)
            if (layer != null){
                val base = when(baseType){
                    IconLayout.BaseTypeEnum.TINT -> layer.findViewById<ImageView>(layer.baseTintId)
                    IconLayout.BaseTypeEnum.LINE -> layer.findViewById<ImageView>(layer.baseLineId)
                }

                base.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
        }
    }
}