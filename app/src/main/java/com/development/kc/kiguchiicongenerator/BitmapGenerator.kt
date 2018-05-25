package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.*
import android.graphics.drawable.VectorDrawable
import android.support.v4.content.ContextCompat

object BitmapGenerator {
    fun textToBitmap(color: Int, text: String, size: Int): Bitmap {
        val paint = Paint()
        var width = size * text.length
        var height = size

        paint.isAntiAlias = true
        paint.color = color
        paint.textSize = size * 1.0f
        //テキストの表示範囲を設定
        paint.getTextBounds(text, 0, text.length, Rect(0, 0, width, height))

        //FontMetrics??
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

    public fun vectorToBitmap(vectorDrawable: VectorDrawable, width: Int, height: Int): Bitmap{
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }
}