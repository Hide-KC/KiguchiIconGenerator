package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

//実際にレイアウトファイルに配置できるレイアウト
class IconLayout(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): this(context, attrs)



    //IconLayoutで１レイヤーに使用するためのレイアウトクラス
    private class PartsBaseLayout(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
        constructor(context: Context) : this(context, null)
        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs)
    }

    private class CommentBaseLayout(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
        constructor(context: Context) : this(context, null)
        constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : this(context, attrs)


    }
}