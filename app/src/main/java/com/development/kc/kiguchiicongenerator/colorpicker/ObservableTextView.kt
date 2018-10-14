package com.development.kc.kiguchiicongenerator.colorpicker

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.development.kc.kiguchiicongenerator.colorpicker.AHSB
import com.development.kc.kiguchiicongenerator.colorpicker.IColorObserver

class ObservableTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs), IColorObserver {
    private var observer: IColorObserver? = null
    fun setObserver(observer: IColorObserver){
        this.observer = observer
    }

    override fun colorUpdate(ahsb: AHSB) {
        observer?.colorUpdate(ahsb)
    }
}