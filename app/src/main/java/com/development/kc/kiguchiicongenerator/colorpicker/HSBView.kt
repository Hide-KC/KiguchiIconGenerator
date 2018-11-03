package com.development.kc.kiguchiicongenerator.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.View

abstract class HSBView : View {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface OnAHSBChangedListener {
        fun onAHSBChanged(ahsb: AHSB)
    }

    var mListener: OnAHSBChangedListener? = null
    fun setOnAHSBChangeListener(listener: OnAHSBChangedListener){
        this.mListener = mListener?: listener
    }

    private val ahsb = AHSB()

    var mAlpha: Int
        get(){
            return ahsb.mAlpha
        }
        set(value){
            ahsb.mAlpha = value.coerceIn(0..255)
        }

    var hue: Float
        get(){
            return ahsb.hue
        }
        set(value){
            var degree = value
            if (degree > 360 || degree < 0){
                //360度以内に格納
                degree %= 360
                degree += 360
                degree %= 360
            }
            ahsb.hue = degree
        }

    var saturation: Float
        get(){
            return ahsb.saturation
        }
        set(value){
            ahsb.saturation = value.coerceIn(0f..1f)
        }

    var brightness: Float
        get(){
            return ahsb.brightness
        }
        set(value){
            ahsb.brightness = value.coerceIn(0f..1f)
        }

    fun getAHSB(): AHSB{
        return ahsb
    }

    fun copyAHSB(): AHSB{
        return ahsb.copy(mAlpha = ahsb.mAlpha, hue = ahsb.hue, saturation = ahsb.saturation, brightness = ahsb.brightness)
    }

    fun setAHSB(ahsb: AHSB){
        this.ahsb.mAlpha = ahsb.mAlpha
        this.ahsb.hue = ahsb.hue
        this.ahsb.saturation = ahsb.saturation
        this.ahsb.brightness = ahsb.brightness
    }
}