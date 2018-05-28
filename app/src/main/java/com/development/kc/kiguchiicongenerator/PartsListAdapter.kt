package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.support.constraint.Guideline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

class PartsListAdapter(context: Context) : ArrayAdapter<Int>(context, android.R.layout.simple_list_item_1){
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = convertView ?: inflater.inflate(R.layout.parts_base, null).apply { tag = PartsViewHolder(this) }

        val holder = cv.tag as PartsViewHolder


        return cv
    }


    inner class PartsViewHolder(view: View){
        val backView = view.findViewById<ImageView>(R.id.base_back)
        val lineView = view.findViewById<ImageView>(R.id.base_line)
        val rightGuide = view.findViewById<Guideline>(R.id.base_line_right)
        val leftGuide = view.findViewById<Guideline>(R.id.base_line_left)
        val topGuide = view.findViewById<Guideline>(R.id.base_line_top)
    }
}