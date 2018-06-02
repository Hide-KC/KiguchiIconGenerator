package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.*

object DrawableController {
    //文字列をBitmapにして返す
    fun textToBitmap(context: Context, color: Int, text: String, size: Int): Bitmap {
        val paint = Paint()
        var width = size * text.length
        var height = size

        //フォントの選択
        val typeface = Typeface.createFromAsset(context.assets, "fonts/mini_wakuwaku.otf")
        paint.typeface = typeface

        paint.isAntiAlias = true
        paint.color = color
        paint.textSize = size * 1f
        //テキストの表示範囲を設定
        paint.getTextBounds(text, 0, text.length, Rect(0, 0, width, height))

        //FontMetrics??ベースライン合わせ、トップ合わせなどに使う
        val fontMetrics = paint.fontMetrics
        width = paint.measureText(text).toInt()
        height = (Math.abs(fontMetrics.top) + fontMetrics.bottom).toInt()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawText(text, 0f, Math.abs(fontMetrics.top), paint)

        return bitmap
    }
}