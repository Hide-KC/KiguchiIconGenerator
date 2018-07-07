package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class ObservableTextView(context: Context, attrs: AttributeSet?): TextView(context, attrs), IObserver{
    private var mListener: IObserver? = null
    fun setObserver(observer: IObserver){
        mListener = observer
    }

    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float){
        mListener?.colorUpdate(hue, saturation, brightness)
    }

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs)
}