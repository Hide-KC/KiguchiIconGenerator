package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat

object BitmapGenerator {
    fun textToBitmap(context: Context, text: String, size: Int): Bitmap {
        val paint = Paint()
        var width = size * text.length
        var height = size

        paint.isAntiAlias = true
        paint.color = getMyColor(context, android.R.color.black)
        paint.textSize = size * 1.0f
        //テキストの表示範囲を設定
        paint.getTextBounds(text, 0, text.length, Rect(0, 0, width, height))

        val fontMetrics = paint.fontMetrics
        width = paint.measureText(text).toInt()
        height = (Math.abs(fontMetrics.top) + fontMetrics.bottom).toInt()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawText(text, 0f, Math.abs(fontMetrics.top), paint)

        return bitmap

    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(context: Context, id: Int): Int{
        return ContextCompat.getColor(context, id)
    }
}