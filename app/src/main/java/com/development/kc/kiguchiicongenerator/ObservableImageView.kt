package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.ColorFilter
import android.util.AttributeSet
import android.widget.ImageView

//IObserver持たせたかっただけ
class ObservableImageView(context: Context, attrs: AttributeSet?): ImageView(context, attrs), IObserver{
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs)

    private var mListener: IObserver? = null
    private var color: Int = 0

    fun setObserver(observer: IObserver){
        mListener = observer
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float){
        mListener?.colorUpdate(hue, saturation, brightness)
    }
}