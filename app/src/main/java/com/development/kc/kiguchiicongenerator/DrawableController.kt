package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.*
import android.os.Environment
import android.support.design.internal.BaselineLayout
import android.support.graphics.drawable.VectorDrawableCompat
import android.widget.Toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

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

    fun saveAsPngImage(context: Context, icon: IconDTO){
        val paint = Paint()
        val size = context.resources.getDimension(R.dimen.icon_resolution).toInt()

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        try {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            val file = File(dir.path + "/", getFileName())
            val outStream = FileOutputStream(file)

            val groups = IconLayout.GroupEnum.values()
            val builder = StringBuilder()
            for (i_group in 0 until groups.size){
                //「ic_Group_PartsId_」まで生成
                builder.append("ic_").append(groups[i_group].groupStr).append("_").append(String.format("%03d", icon.getPartsId(groups[i_group]))).append("_")
                val tintVDId = context.resources.getIdentifier(builder.toString() + "tint", "drawable", context.packageName)
                val lineVDId = context.resources.getIdentifier(builder.toString() + "line", "drawable", context.packageName)

                if (tintVDId != 0){
                    val tintVD = VectorDrawableCompat.create(context.resources, tintVDId, null)
                    tintVD?.setBounds(0,0,canvas.width, canvas.height)
                    tintVD?.setColorFilter(icon.getColorFilter(groups[i_group], IconLayout.BaseTypeEnum.TINT), PorterDuff.Mode.SRC_ATOP)
                    tintVD?.draw(canvas)
                }

                if (lineVDId != 0){
                    val lineVD = VectorDrawableCompat.create(context.resources, lineVDId, null)
                    lineVD?.setBounds(0,0,canvas.width, canvas.height)
                    lineVD?.setColorFilter(icon.getColorFilter(groups[i_group], IconLayout.BaseTypeEnum.LINE), PorterDuff.Mode.SRC_ATOP)
                    lineVD?.draw(canvas)
                }
                builder.setLength(0)
            }

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.close()
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException){
            e.printStackTrace()
        } finally {
            bitmap.recycle()
        }
    }

    private fun getFileName(): String{
        val c = Calendar.getInstance()
        val builder = StringBuilder()
        builder.append("ICN_")
                .append(c.get(Calendar.YEAR))
                .append(c.get(Calendar.MONTH))
                .append(c.get(Calendar.DAY_OF_MONTH))
                .append(c.get(Calendar.HOUR_OF_DAY))
                .append(c.get(Calendar.MINUTE))
                .append(c.get(Calendar.SECOND))
                .append(".png")
        return builder.toString()
    }
}