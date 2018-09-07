package com.development.kc.kiguchiicongenerator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent

@SuppressWarnings("MagicNumber")
class HueBar: AbsHSBView{
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        //識別子のセット
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HueBar, defStyleAttr, 0)
        try{
            //xmlで静的にセットされている値の取出し
            setHue(typedArray.getFloat(R.styleable.HueBar_hue, 0f))
            strokeSize = typedArray.getDimension(R.styleable.HueBar_stroke_size, 2 * context.resources.displayMetrics.density)
            pick = typedArray.getFloat(R.styleable.HueBar_pick, 0f)
        } finally {
            typedArray.recycle()
        }
    }

    private val rainbowArray: IntArray = intArrayOf(
        Color.parseColor("#ffff0000"),
        Color.parseColor("#ffffff00"),
        Color.parseColor("#ff00ff00"),
        Color.parseColor("#ff00ffff"),
        Color.parseColor("#ff0000ff"),
        Color.parseColor("#ffff00ff"),
        Color.parseColor("#ffff0000")
    )

    private var strokeSize: Float
    private var pick: Float
    private var verticalGridSize: Float = 0f
    private var rainbowBaseLine: Float = 0f
    private var showPreview = false

    private val rainbowBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val rainbowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val pickPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        drawPicker(canvas)
        drawColorAim(canvas, rainbowBaseLine,verticalGridSize.toInt() / 2, verticalGridSize * 0.5f, Color.HSVToColor(floatArrayOf(getHSB()[0], 1f, 1f)))
        if (showPreview){
            drawColorAim(canvas, verticalGridSize, (verticalGridSize / 1.4f).toInt(), verticalGridSize * 0.7f, Color.HSVToColor(floatArrayOf(getHSB()[0], 1f, 1f)))
        }
    }

    private fun drawPicker(canvas: Canvas){
        val lineX = verticalGridSize / 2f
        val lineY = rainbowBaseLine
        rainbowPaint.strokeWidth = verticalGridSize / 1.5f + strokeSize
        rainbowBackgroundPaint.strokeWidth = rainbowPaint.strokeWidth + strokeSize
        canvas.drawLine(lineX, lineY, width - lineX, lineY, rainbowBackgroundPaint)
        canvas.drawLine(lineX, lineY, width - lineX, lineY, rainbowPaint)
    }

    private fun drawColorAim(canvas: Canvas, baseLine: Float, offset: Int, size: Float, color: Int){
        val circleCenterX = offset + pick * (canvas.width - offset * 2)
        canvas.drawCircle(circleCenterX, baseLine, size, pickPaint.apply { this.color = Color.WHITE })
        canvas.drawCircle(circleCenterX, baseLine, size - strokeSize, pickPaint.apply { this.color = color })
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val shader = LinearGradient(measuredHeight / 4f, measuredHeight / 2f, measuredWidth - measuredHeight / 4f, measuredHeight / 2f, rainbowArray, null, Shader.TileMode.CLAMP)
        verticalGridSize = measuredHeight / 3f
        rainbowPaint.shader = shader
        rainbowBaseLine = verticalGridSize / 2f + verticalGridSize * 2
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        if (event != null && (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN)){
            pick = event.x / measuredWidth.toFloat()
            if (pick < 0) {
                pick = 0f
            } else if (pick > 1) {
                pick = 1f
            }
            setHue(pick * 360)
            showPreview = true
        } else if (action == MotionEvent.ACTION_UP){
            showPreview = false
        }
        mListener?.onHSBChanged(getHue(), getSaturation(), getBrightness())
        return true
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
        setHSB(hue, saturation, brightness)
        postInvalidateOnAnimation()
    }
}