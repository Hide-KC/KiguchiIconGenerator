package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.support.constraint.Guideline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class GroupListAdapter(context: Context) : ArrayAdapter<Int> (context, android.R.layout.simple_list_item_1) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = convertView?: inflater.inflate(R.layout.parts_base, null).apply {
            tag = ItemViewHolder(this)
        }

        val holder = cv.tag as ItemViewHolder
        holder.lineView.setImageResource(getItem(position))
        return cv
    }


    inner class ItemViewHolder(view: View){
        val lineView = view.findViewById<ImageView>(R.id.base_line)
    }


}