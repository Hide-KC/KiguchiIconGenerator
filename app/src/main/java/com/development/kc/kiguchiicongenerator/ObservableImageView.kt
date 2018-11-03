package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.development.kc.kiguchiicongenerator.colorpicker.AHSB
import com.development.kc.kiguchiicongenerator.colorpicker.IColorObserver

//IObserver持たせたかっただけ
class ObservableImageView: ImageView, IColorObserver{
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private var mListener: IColorObserver? = null
    private var color: Int = 0

    fun setObserver(colorObserver: IColorObserver){
        mListener = colorObserver
    }

    override fun colorUpdate(ahsb: AHSB){
        mListener?.colorUpdate(ahsb)
    }
}