package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.util.AttributeSet
import android.view.View

abstract class AbsHSBView(context: Context, attrs: AttributeSet?) : View(context, attrs), IObserver {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): this(context, attrs)

    interface OnHSBChangedListener {
        fun onHSBChanged(hue: Float, saturation: Float, brightness: Float)
    }

    private var mAlpha: Float = 1f
    private var mHue: Float = 0f
    private var mSaturation: Float = 0f
    private var mBrightness: Float = 0f
    fun getAlphaVal(): Float{
        return mAlpha
    }
    fun getHSB(): FloatArray{
        return floatArrayOf(mHue, mSaturation, mBrightness)
    }
    fun getHue(): Float{
        return mHue
    }
    fun getSaturation(): Float{
        return mSaturation
    }
    fun getBrightness(): Float{
        return mBrightness
    }
    fun setAlphaVal(alpha: Float){
        when {
            alpha < 0f -> this.mAlpha = 0f
            alpha > 1f -> this.mAlpha = 1f
            else -> this.mAlpha = alpha
        }
    }
    fun setHSB(hue: Float, saturation: Float, brightness: Float){
        setHue(hue)
        setSaturation(saturation)
        setBrightness(brightness)
    }
    fun setHue(hue: Float){
        //0～360以内に収める
        var d = hue
        if (d > 360 || d < 0){
            d %= 360
            d += 360
            d %= 360
        }
        this.mHue = d
    }
    fun setSaturation(saturation: Float){
        when {
            saturation < 0f -> this.mSaturation = 0f
            saturation > 1f -> this.mSaturation = 1f
            else -> this.mSaturation = saturation
        }
    }
    fun setBrightness(brightness: Float){
        when {
            brightness < 0f -> this.mBrightness = 0f
            brightness > 1f -> this.mBrightness = 1f
            else -> this.mBrightness = brightness
        }
    }

    public var mListener: AbsHSBView.OnHSBChangedListener? = null
    fun setOnHSBChangeListener(listener: OnHSBChangedListener){
        if (mListener == null){
            this.mListener = listener
        }
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {

    }
}