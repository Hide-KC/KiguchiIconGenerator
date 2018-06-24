package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class HistoryListAdapter(context: Context) : ArrayAdapter<HistoryDTO>(context, android.R.layout.simple_list_item_1) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = convertView?: inflater.inflate(R.layout.history_views, null).apply {
            tag = ItemViewHolder(this)
        }

        val holder = cv.tag as ItemViewHolder

        return cv
    }

    inner class ItemViewHolder(view: View){

    }
}