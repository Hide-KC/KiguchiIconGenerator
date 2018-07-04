package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView

class PartsGridFragment: AbsFragment() {
    interface OnPartsClickListener{
        fun onPartsClicked(resStr: String)
    }

    private var partsClickListener: OnPartsClickListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnPartsClickListener){
            partsClickListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.parts_grid_fragment, container, false)
        val gridView = view.findViewById<GridView>(R.id.parts_grid)
        gridView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val adapter = gridView.adapter as PartsGridAdapter
            partsClickListener?.onPartsClicked(adapter.getItem(i).resString)
        }

        if (this.context != null){
            val partsGridAdapter = PartsGridAdapter(this.context!!)
            val resNames = ArrayList<String>()
            resNames.add(subStringResName(resources.getResourceEntryName(R.drawable.ic_backhair_001_line), 4))
            resNames.add(subStringResName(resources.getResourceEntryName(R.drawable.ic_bang_001_line), 4))
            resNames.add(subStringResName(resources.getResourceEntryName(R.drawable.ic_bang_002_line), 4))
            resNames.add(subStringResName(resources.getResourceEntryName(R.drawable.ic_mouth_001_line), 4))
            for (name in resNames){
                partsGridAdapter.add(PartsDTO(name, Color.YELLOW, Color.parseColor("#ff555555")))
            }

            gridView.adapter = partsGridAdapter
        }

        return view
    }

    companion object {
        fun newInstance(targetFragment: Fragment?, targetGroupResStr: String): PartsGridFragment{
            val args = Bundle()
            val fragment = PartsGridFragment()
            fragment.arguments = args
            fragment.setTargetFragment(targetFragment, 0)
            return fragment
        }
    }

    private fun subStringResName(resName: String, cutLength: Int): String{
        return if (resName.length - cutLength <= 0){
            ""
        } else {
            resName.substring(0, resName.length - cutLength)
        }
    }
}