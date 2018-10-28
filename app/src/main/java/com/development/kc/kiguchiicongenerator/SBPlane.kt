package com.development.kc.kiguchiicongenerator

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * TODO: document your custom view class.
 */
class SBPlane: AbsHSBView{
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        //識別子のセット
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SBPlane, 0, 0)
        try{
            //xmlで静的にセットされている値の取出し
            setHue(typedArray.getFloat(R.styleable.SBPlane_sbplane_hue, 0f))
        } finally {
            typedArray.recycle()
        }
    }

    private val viewSize: FloatArray = floatArrayOf(0f, 0f)
    private lateinit var paint: Paint
    private lateinit var lg: LinearGradient

    init {

    }

    //onDrawの前に発生
    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewSize[0] = this.measuredWidth.toFloat()
        viewSize[1] = this.measuredHeight.toFloat()

        //ShaderはonDrawで逐次生成
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        //端数処理で線が細すぎる状態になる場合があるため、端数切り上げ
        //線が細くなる…dpiの関係？
        paint.strokeWidth = Math.ceil((viewSize[0] / 100).toDouble()).toFloat() * context.resources.displayMetrics.density
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //canvasサイズはここで取得可能だが、頻繁に呼ばれるためonMeasureで取得
        //グラデーション線を縦方向に描画
        val unit = viewSize[0] / 100f
        for (ix in 0..100){
            val startColor = Color.HSVToColor(floatArrayOf(getHue(), ix / 100f, 1f))
            val endColor = Color.HSVToColor(floatArrayOf(getHue(), ix / 100f, 0f))

            //ここの座標系の数値の入力はあまり意味がない？startX,endX=0fでも動作確認。
            lg = LinearGradient(unit * ix, 0f, unit * ix, viewSize[1], startColor, endColor, Shader.TileMode.CLAMP)
            paint.shader = lg
            canvas?.drawLine(unit * ix, 0f, unit * ix, viewSize[1], paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        if (event != null && (action == MotionEvent.ACTION_MOVE || action == MotionEvent.ACTION_DOWN)){
            val x = event.x / measuredWidth.toFloat()
            val y = event.y / measuredHeight.toFloat()
            when {
                x < 0f -> setSaturation(0f)
                x > 1f -> setSaturation(1f)
                else -> setSaturation(x)
            }

            when {
                y < 0f -> setBrightness(1f)
                y > 1f -> setBrightness(0f)
                else -> setBrightness(1 - y)
            }
        }
//        Log.d(this.javaClass.simpleName, "Hue = " + getHue())
        mListener?.onHSBChanged(getHue(), getSaturation(), getBrightness())
        return true
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {
        setHSB(hue, saturation, brightness)
        postInvalidateOnAnimation()
    }
}
