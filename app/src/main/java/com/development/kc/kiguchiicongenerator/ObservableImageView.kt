package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.widget.ImageView

class ObservableImageView(context: Context, attrs: AttributeSet?): ImageView(context, attrs), IObserver {
    override fun colorUpdate(hue: Float, saturation: Float, brightness: Float) {

    }

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): this(context, attrs)


}