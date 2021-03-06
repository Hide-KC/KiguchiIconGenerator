package com.development.kc.colorpicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import com.development.kc.kiguchiicongenerator.R

class SBPlane : HSBView, IColorObserver {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //識別子のセット
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SBPlane, 0, 0)
        try {
            //xmlで静的にセットされている値の取出し
            hue = typedArray.getFloat(R.styleable.SBPlane_sbplane_hue, 0f)
            saturation = typedArray.getFloat(R.styleable.SBPlane_saturation, 0f)
            brightness = typedArray.getFloat(R.styleable.SBPlane_brightness, 1f)
        } finally {
            typedArray.recycle()
        }
    }

    private val viewSize: Square = Square(0, 0)
    private lateinit var paint: Paint
    private lateinit var lg: LinearGradient

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewSize.width = measuredWidth
        viewSize.height = measuredHeight

        //ShaderはonDrawで逐次生成
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = Math.ceil(viewSize.width / 100.0).toFloat() * context.resources.displayMetrics.density
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val unit = viewSize.width / 100f
        for (x_i in 0 until 100){
            val startColor = Color.HSVToColor(floatArrayOf(hue, x_i / 100f, 1f))
            val endColor = Color.HSVToColor(floatArrayOf(hue, x_i / 100f, 0f))

            //ここでの座標系の数値入力は意味が無さそう?
            lg = LinearGradient(x_i*unit , 0f, x_i*unit , viewSize.height*1f, startColor, endColor, Shader.TileMode.CLAMP)
            paint.shader = lg
            canvas?.drawLine(x_i*unit, 0f, x_i*unit, viewSize.height*1f, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        if (event != null && (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN)){
            val x = event.x / measuredWidth.toFloat()
            val y = event.y / measuredHeight.toFloat()
            saturation = x.coerceIn(0f..1f)
            brightness = when {
                y < 0f -> 1f
                y > 1f -> 0f
                else -> 1 - y
            }
        }
        mListener?.onAHSBChanged(getAHSB())
        return true
    }

    override fun colorUpdate(ahsb: AHSB) {
        //HSBをセットして再描画
        mAlpha = ahsb.mAlpha
        hue = ahsb.hue
        saturation = ahsb.saturation
        brightness = ahsb.brightness
        postInvalidateOnAnimation()
    }

    private data class Square(var width: Int, var height: Int)
}