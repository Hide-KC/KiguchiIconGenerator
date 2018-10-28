package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.*
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.layer_base.view.*
import kotlin.math.roundToInt

class PartsBaseLayout: ConstraintLayout {
    lateinit var tag: IconLayout.GroupEnum
    val baseTintId = View.generateViewId()
    val baseLineId = View.generateViewId()
    val leftId = View.generateViewId()
    val rightId = View.generateViewId()
    val topId = View.generateViewId()
    private var scale: Float = 1f
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){

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

        constraintSet.connect(baseTint.id, ConstraintSet.START, leftId, ConstraintSet.START)
        constraintSet.connect(baseTint.id, ConstraintSet.END, rightId, ConstraintSet.START)
        constraintSet.connect(baseTint.id, ConstraintSet.TOP, topId, ConstraintSet.TOP)
        constraintSet.connect(baseTint.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

//        constraintSet.connect(baseTint.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
//        constraintSet.connect(baseTint.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
//        constraintSet.connect(baseTint.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
//        constraintSet.connect(baseTint.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        constraintSet.constrainWidth(baseTint.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(baseTint.id, ConstraintSet.MATCH_CONSTRAINT)


        constraintSet.connect(baseLine.id, ConstraintSet.START, leftId, ConstraintSet.START)
        constraintSet.connect(baseLine.id, ConstraintSet.END, rightId, ConstraintSet.START)
        constraintSet.connect(baseLine.id, ConstraintSet.TOP, topId, ConstraintSet.TOP)
        constraintSet.connect(baseLine.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)

//        constraintSet.connect(baseLine.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0)
//        constraintSet.connect(baseLine.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0)
//        constraintSet.connect(baseLine.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0)
//        constraintSet.connect(baseLine.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0)

        constraintSet.constrainWidth(baseLine.id, ConstraintSet.MATCH_CONSTRAINT)
        constraintSet.constrainHeight(baseLine.id, ConstraintSet.MATCH_CONSTRAINT)

        constraintSet.applyTo(this)
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

    fun setComment(comment: String){
        val paint = Paint()
        val textSize = 20f

        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.textSize = textSize
        val fontMetrics = paint.fontMetrics
        paint.getTextBounds(comment, 0, comment.length, Rect(0,0,(textSize * comment.length).roundToInt(),textSize.toInt()))

        val textWidth = paint.measureText(comment).roundToInt()
        val textHeight = (Math.abs(fontMetrics.top) + fontMetrics.bottom).roundToInt()
        val bitmap = Bitmap.createBitmap(textWidth, textHeight,Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        canvas.drawText(comment, 0f, Math.abs(fontMetrics.top), paint)

        base_tint.setImageBitmap(bitmap)


    }
}