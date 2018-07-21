package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class PartsGridAdapter(context: Context) : ArrayAdapter<PartsDTO>(context, android.R.layout.simple_list_item_1){
    private val inflater = LayoutInflater.from(context)
    private val DRAWABLE = "drawable"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //選択するパーツのみの表示
        val cv = convertView?: inflater.inflate(R.layout.parts_grid_item, null).also {
            it.tag = PartsViewHolder(it)
        }

        val holder = cv.tag as PartsViewHolder
        val item = getItem(position)
        val resString = item.resString









        val tintResName = resString.replace("line", "tint")
        val tintResID = context.resources.getIdentifier(tintResName, DRAWABLE, context.packageName)
        holder.tintView.setImageResource(tintResID)
        if (item.tintColor != null){
            holder.tintView.setColorFilter(item.tintColor)
        }

        val lineResName = resString.replace("tint", "line")
        val lineResID = context.resources.getIdentifier(lineResName, DRAWABLE, context.packageName)
        holder.lineView.setImageResource(lineResID)
        if (item.lineColor != null){
            holder.lineView.setColorFilter(item.lineColor)
        }

        return cv
    }

    private class PartsViewHolder(view: View){
        val tintView = view.findViewById<ImageView>(R.id.base_tint)
        val lineView = view.findViewById<ImageView>(R.id.base_line)
    }
}