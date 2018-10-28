package com.development.kc.kiguchiicongenerator

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class IconViewFragment: Fragment() {
    interface OnIconUpdatedListener{
        fun iconUpdate(group: IconLayout.GroupEnum, partsId: Int, tintColor: Int, lineColor: Int)
        fun partsSelected(group: IconLayout.GroupEnum, partsId: Int)
    }

    //TODO IconDTOをIconViewFragment内で管理する。MainActivityからは除去
    private val icon: IconDTO = IconDTO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.icon_views2, container, false)
        //Icon表示措置
        val iconLayout = view.findViewById<IconLayout>(R.id.icon_layout)
        for (group in IconLayout.GroupEnum.values()){
            iconLayout.setParts(group, icon.getPartsId(group))
            for (type in IconLayout.BaseTypeEnum.values()){
                iconLayout.setColorFilter(group, type, icon.getColorFilter(group, type))
            }
        }

        return view
    }

    fun iconUpdate(group: IconLayout.GroupEnum, partsId: Int, tintColor: Int, lineColor: Int){
        icon.setPartsId(group, partsId)
        icon.setColorFilter(group, IconLayout.BaseTypeEnum.TINT, tintColor)
        icon.setColorFilter(group, IconLayout.BaseTypeEnum.LINE, lineColor)

        val iconLayout = view?.findViewById<IconLayout>(R.id.icon_layout) //PartsBaseLayoutを内包するIconLayoutへのキャスト

        iconLayout?.setParts(group, partsId)
        if (group == IconLayout.GroupEnum.BANG || group == IconLayout.GroupEnum.BACK_HAIR){
            iconLayout?.setColorFilter(IconLayout.GroupEnum.BACK_HAIR, IconLayout.BaseTypeEnum.TINT, tintColor)
            iconLayout?.setColorFilter(IconLayout.GroupEnum.BACK_HAIR, IconLayout.BaseTypeEnum.LINE, lineColor)
            iconLayout?.setColorFilter(IconLayout.GroupEnum.BANG, IconLayout.BaseTypeEnum.TINT, tintColor)
            iconLayout?.setColorFilter(IconLayout.GroupEnum.BANG, IconLayout.BaseTypeEnum.LINE, lineColor)
        } else {
            iconLayout?.setColorFilter(group, IconLayout.BaseTypeEnum.TINT, tintColor)
            iconLayout?.setColorFilter(group, IconLayout.BaseTypeEnum.LINE, lineColor)
        }
    }

    fun partsSelected(group: IconLayout.GroupEnum, partsId: Int){
        iconUpdate(group, partsId, icon.getColorFilter(group, IconLayout.BaseTypeEnum.TINT), icon.getColorFilter(group, IconLayout.BaseTypeEnum.LINE))
    }

    fun commentUpdate(comment: String?){
        icon.setComment(comment)
    }

    companion object {
        fun newInstance(targetFragment: Fragment?): IconViewFragment{
            val fragment = IconViewFragment()
            fragment.setTargetFragment(targetFragment, 0)
            return fragment
        }
    }

    //sdk23からはgetColorが非推奨になり、ContextCompat.getColorを使うようになりました。
    private fun getMyColor(id: Int): Int{
        return if(context != null) {
            ContextCompat.getColor(context!!, id)
        } else {
            return 0
        }
    }

    fun getIcon(): IconDTO{
        return icon
    }
}