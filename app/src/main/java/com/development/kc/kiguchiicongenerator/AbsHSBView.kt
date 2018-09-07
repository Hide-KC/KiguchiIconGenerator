package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.util.AttributeSet
import android.view.View

abstract class AbsHSBView: View, IColorObserver {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

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
        mSaturation = when {
            saturation < 0f -> 0f
            saturation > 1f -> 1f
            else -> saturation
        }
    }
    fun setBrightness(brightness: Float){
        mBrightness = when {
            brightness < 0f -> 0f
            brightness > 1f -> 1f
            else -> brightness
        }
    }

    var mListener: AbsHSBView.OnHSBChangedListener? = null
    fun setOnHSBChangeListener(listener: OnHSBChangedListener){
        this.mListener = mListener?: listener
    }
}