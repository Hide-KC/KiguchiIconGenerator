package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Parcelable
import android.support.constraint.ConstraintHelper
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Guideline
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class PartsBaseLayout(context: Context, attrs: AttributeSet?, val tag: IconLayout.GroupEnum?): ConstraintLayout(context, attrs) {
    constructor(context: Context): this(context, null, null)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): this(context, attrs, null)
    constructor(context: Context, tag: IconLayout.GroupEnum): this(context, null, tag)

    val baseTintId = View.generateViewId()
    val baseLineId = View.generateViewId()
    val leftId = View.generateViewId()
    val rightId = View.generateViewId()
    val topId = View.generateViewId()
    private var scale: Float = 1f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    init {
        val baseTint = ImageView(context).also {
            it.id = baseTintId
            it.scaleType = ImageView.ScaleType.FIT_CENTER
            it.setBackgroundColor(Color.TRANSPARENT)
        }
        this.addView(baseTint)

        val baseLine = ImageView(context).also {
            it.id = baseLineId
            it.scaleType = ImageView.ScaleType.FIT_CENTER
            it.setBackgroundColor(Color.TRANSPARENT)
        }
        this.addView(baseLine)

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.create(leftId, ConstraintSet.VERTICAL_GUIDELINE)
        constraintSet.setGuidelinePercent(leftId, 0f)

        constraintSet.create(rightId, ConstraintSet.VERTICAL_GUIDELINE)
        constraintSet.setGuidelinePercent(rightId, 1f)

        constraintSet.create(topId, ConstraintSet.HORIZONTAL_GUIDELINE)
        constraintSet.setGuidelinePercent(topId, 0f)

//        constraintSet.connect(baseTint.id, ConstraintSet.START, leftId, ConstraintSet.START)
//        constraintSet.connect(baseTint.id, ConstraintSet.END, rightId, ConstraintSet.START)
//        constraintSet.connect(baseTint.id, ConstraintSet.TOP, topId, ConstraintSet.TOP)
//        constraintSet.connect(baseTint.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

        constraintSet.connect(baseTint.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constraintSet.connect(baseTint.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        constraintSet.connect(baseTint.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        constraintSet.connect(baseTint.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        constraintSet.constrainWidth(baseTint.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(baseTint.id, ConstraintSet.MATCH_CONSTRAINT)


//        constraintSet.connect(baseLine.id, ConstraintSet.START, leftId, ConstraintSet.START)
//        constraintSet.connect(baseLine.id, ConstraintSet.END, rightId, ConstraintSet.START)
//        constraintSet.connect(baseLine.id, ConstraintSet.TOP, topId, ConstraintSet.TOP)
//        constraintSet.connect(baseLine.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

        constraintSet.connect(baseLine.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
        constraintSet.connect(baseLine.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
        constraintSet.connect(baseLine.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
        constraintSet.connect(baseLine.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        constraintSet.constrainWidth(baseLine.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(baseLine.id, ConstraintSet.MATCH_CONSTRAINT)

        constraintSet.applyTo(this)

        //識別子のセット、ScaleType、BackGround、Constraintの設定
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SBPlane, 0, 0)
//        try{
//            //xmlで静的にセットされている値の取出し
//            setHue(typedArray.getFloat(R.styleable.SBPlane_sb_plane_hue, 0f))
//        } finally {
//            typedArray.recycle()
//        }
    }

    override fun onSaveInstanceState(): Parcelable {
        return super.onSaveInstanceState()
    }

    fun setColorFilter(baseType: IconLayout.BaseTypeEnum, color: Int){
        val base = when(baseType){
            IconLayout.BaseTypeEnum.TINT -> findViewById<ImageView>(baseTintId)
            IconLayout.BaseTypeEnum.LINE -> findViewById<ImageView>(baseLineId)
        }
        base?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    //1:1のためXYの倍率は同値
    fun setScale(scale: Float){
        this.scale = scale
    }

    //XYシフト値の絶対値（パーセント）を指定
    fun setOffset(offsetX: Float, offsetY: Float){
        this.offsetX = offsetX
        this.offsetY = offsetY
    }

    //カラーフィルタをデフォルト値に戻す
    fun setDefaultColor(){

    }

    //シフト値、スケールを戻す
    fun resetPosition(){
        scale = 1f
        offsetX = 0f
        offsetY = 0f
    }


}