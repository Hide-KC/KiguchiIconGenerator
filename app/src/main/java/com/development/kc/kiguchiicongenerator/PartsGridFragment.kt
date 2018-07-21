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
import java.lang.reflect.Modifier

class PartsGridFragment: AbsFragment() {
    interface OnPartsClickListener{
        fun onPartsClicked(resStr: String)
    }

    private var group: IconLayout.GroupEnum = IconLayout.GroupEnum.BACK_HAIR
    private var partsClickListener: OnPartsClickListener? = null
    private lateinit var icon: IconDTO

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

        val args = arguments
        if (args != null){
            val ordinal = args.getInt("group", 0)
            group = IconLayout.GroupEnum.values()[ordinal]
            icon = args.getSerializable("icon") as IconDTO
        }

        if (context != null){
            val partsGridAdapter = PartsGridAdapter(context!!)
            val resNames = ArrayList<String>()
            //リフレクションによりDrawableId一覧を生成
            val resIds = R.drawable::class.java.fields
                    .filter {
                        Modifier.isPublic(it.modifiers)
                        && Modifier.isStatic(it.modifiers)
                        && Modifier.isFinal(it.modifiers)
                    }
                    .map {
                        it.getInt(null)
                    }

            for (id in resIds){
                //TODO tintしかないパターン未対応
                val name = resources.getResourceEntryName(id)
                if (name.contains(group.groupStr) && name.contains("line")){
                    resNames.add(name)
                }
            }

            for (name in resNames){
                //TODO tint_color, line_colorをどう設定する？
                partsGridAdapter.add(PartsDTO(name, Color.YELLOW, Color.parseColor("#ff555555")))
            }

            gridView.adapter = partsGridAdapter
        }

        return view
    }

    companion object {
        fun newInstance(targetFragment: Fragment?, group: IconLayout.GroupEnum, icon: IconDTO): PartsGridFragment{
            val args = Bundle()
            args.putInt("group", group.ordinal)
            args.putSerializable("icon", icon)
            val fragment = PartsGridFragment()
            fragment.arguments = args
            fragment.setTargetFragment(targetFragment, 0)
            return fragment
        }
    }


}