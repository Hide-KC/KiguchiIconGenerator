package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class ItemListAdapter(context: Context) : ArrayAdapter<String> (context, android.R.layout.simple_list_item_1) {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getView(position, convertView, parent)
    }


    class ItemViewHolder(view: View){
        init {

        }
    }


}