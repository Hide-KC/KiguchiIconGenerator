package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView

class IconLayout(context: Context) : FrameLayout(context) {
    constructor(context: Context, attrs: AttributeSet): this(context)
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

    override fun onSaveInstanceState(): Parcelable {
        return super.onSaveInstanceState()
    }

    init {
        for (i in 0..(groupList.size - 1)){
            this.addView(groupList[i])
            groupList[i].layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        //識別子のセット
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SBPlane, 0, 0)
//        try{
//            //xmlで静的にセットされている値の取出し
//            setHue(typedArray.getFloat(R.styleable.SBPlane_sb_plane_hue, 0f))
//        } finally {
//            typedArray.recycle()
//        }
    }

    fun test(){
        val g = getLayer(GroupEnum.BACK_HAIR)
        if (g != null){
            val u = g.findViewById<Guideline>(g.leftId)
        }
    }

    fun getLayer(tag: GroupEnum): PartsBaseLayout?{
        var layer: PartsBaseLayout? = null
        for (l in groupList){
            if (l.tag == tag){
                layer = l
            }
        }
        return layer
    }



}