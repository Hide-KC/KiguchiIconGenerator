package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

//IObserver持たせたかっただけ
class ObservableTextView: TextView, IColorObserver{
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private var mListener: IColorObserver? = null

    fun setObserver(colorObserver: IColorObserver){
        mListener = colorObserver
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float){
        mListener?.colorUpdate(hue, saturation, brightness)
    }
}