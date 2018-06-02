package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class SVPlaneView(context: Context, attrs: AttributeSet?) : View(context, attrs){
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): this(context, attrs)

    interface OnHSBChangedListener {
        fun onHSBChanged()
    }

    private var mListener: OnHSBChangedListener? = null
    private var hue: Float
    private var saturation: Float = 0f
    private var brightness: Float = 0f
    private val viewSize: FloatArray = floatArrayOf(0f, 0f)
    private lateinit var paint: Paint
    private lateinit var lg: LinearGradient

    fun getHue(): Float{
        return hue
    }
    fun setHue(newHue: Float){
        hue = packDegree(newHue)
    }

    init {
        //識別子のセット
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SVPlaneView, 0, 0)
        try{
            //xmlで静的にセットされている値の取出し
            hue = typedArray.getFloat(R.styleable.SVPlaneView_hue, 0f)
        } finally {
            typedArray.recycle()
        }
    }

    //onDrawの前に発生
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewSize[0] = this.measuredWidth.toFloat()
        viewSize[1] = this.measuredHeight.toFloat()

        //ShaderはonDrawで逐次生成
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        //端数処理で線が細すぎる状態になる場合があるため、端数切り上げ
        paint.strokeWidth = Math.ceil((viewSize[0] / 100).toDouble()).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //canvasサイズはここで取得可能だが、頻繁に呼ばれるためonMeasureで取得
        //グラデーション線を縦方向に描画
        val unit = viewSize[0] / 100f
        for (ix in 0..100){
            val startColor = Color.HSVToColor(floatArrayOf(hue, ix / 100f, 1f))
            val endColor = Color.HSVToColor(floatArrayOf(hue, ix / 100f, 0f))

            //ここの座標系の数値の入力はあまり意味がない？startX,endX=0fでも動作確認。
            lg = LinearGradient(unit * ix, 0f, unit * ix, viewSize[1], startColor, endColor, Shader.TileMode.CLAMP)
            paint.shader = lg
            canvas?.drawLine(unit * ix, 0f, unit * ix, viewSize[1], paint)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    //0～360°以内に収める
    private fun packDegree(degree: Float): Float{
        var d = degree
        d %= 360
        d += 360
        d %= 360
        return d
    }

    fun setOnHSBChangeListener(listener: OnHSBChangedListener){
        if (mListener == null){
            this.mListener = listener
        }
    }
}
