package com.development.kc.kiguchiicongenerator

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class IconViewFragment: Fragment() {
    enum class GroupEnum(val groupStr: String) {
        BACK_HAIR("backhair"),
        BODY("body"),
        FACE("face"),
        EYE("eye"),
        MOUTH("mouth"),
        BANG("bang"),
        HAIR_ACC("hairacc"),
        FACE_ACC("faceacc");


        companion object {
            fun getTypeByValue(value: String): GroupEnum{
                for (g in GroupEnum.values()){
                    if (g.groupStr == value){
                        return g
                    }
                }
                throw IllegalArgumentException("undefined : $value")
            }
        }
    }

    interface OnIconUpdateListener{
        fun iconUpdate(group: GroupEnum, partsId: Int, tintColor: Int, lineColor: Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.icon_views2, container, false)
        //Icon表示措置
        val values = GroupEnum.values()
        for (v in values){
            val tintColor: Int =
                    when(v){
                        GroupEnum.BACK_HAIR -> getMyColor(R.color.dkgly)
                        GroupEnum.BANG -> getMyColor(R.color.dkgly)
                        GroupEnum.BODY -> getMyColor(R.color.pale_orange)
                        GroupEnum.MOUTH -> getMyColor(R.color.pink)
                        else -> 0
                    }
            val lineColor = getMyColor(R.color.black)

            val group = v.groupStr
            val layout = view.findViewById<ConstraintLayout>(context!!.resources.getIdentifier(group, "id", context!!.packageName))
            val baseTint = layout?.findViewById<ImageView>(R.id.base_tint)
            val baseLine = layout?.findViewById<ImageView>(R.id.base_line)

            val builder = StringBuilder()
            builder.append("ic_").append(group).append("_").append("001").append("_")
            val tintDrawableId = context!!.resources.getIdentifier(builder.toString() + "tint", "drawable", context!!.packageName)
            val lineDrawableId = context!!.resources.getIdentifier(builder.toString() + "line", "drawable", context!!.packageName)

            if (tintDrawableId != 0){
                val tintDrawable = VectorDrawableCompat.create(context!!.resources, tintDrawableId, null)
                baseTint?.setImageDrawable(tintDrawable)
                if (tintColor > 0){
                    baseTint?.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP)
                }
            }

            if (lineDrawableId != 0){
                val lineDrawable = VectorDrawableCompat.create(context!!.resources, lineDrawableId, null)
                baseLine?.setImageDrawable(lineDrawable)
                baseLine?.setColorFilter(lineColor, PorterDuff.Mode.SRC_ATOP)
            }
        }

        return view
    }

    fun iconUpdate(groupEnum: GroupEnum, partsId: Int, tintColor: Int, lineColor: Int){
        Log.d(this.javaClass.simpleName, groupEnum.groupStr + " : " + partsId)
        val group = groupEnum.groupStr
        val layout = view?.findViewById<ConstraintLayout>(context!!.resources.getIdentifier(group, "id", context!!.packageName))
        val baseTint = layout?.findViewById<ImageView>(R.id.base_tint)
        val baseLine = layout?.findViewById<ImageView>(R.id.base_line)

        val builder = StringBuilder()
        builder.append("ic_").append(group).append("_").append(String.format("%03d", partsId)).append("_")
        val tintDrawableId = context!!.resources.getIdentifier(builder.toString() + "tint", "drawable", context!!.packageName)
        val lineDrawableId = context!!.resources.getIdentifier(builder.toString() + "line", "drawable", context!!.packageName)

        if (tintDrawableId != 0){
            val tintDrawable = VectorDrawableCompat.create(context!!.resources, tintDrawableId, null)
            baseTint?.setImageDrawable(tintDrawable)
            if (tintColor > 0){
                baseTint?.setColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP)
            }
        }

        if (lineDrawableId != 0){
            val lineDrawable = VectorDrawableCompat.create(context!!.resources, lineDrawableId, null)
            baseLine?.setImageDrawable(lineDrawable)
            baseLine?.setColorFilter(lineColor, PorterDuff.Mode.SRC_ATOP)
        }
    }

    companion object {
        fun newInstance(targetFragment: Fragment?, iconDTO: IconDTO): IconViewFragment{
            val fragment = IconViewFragment()
            val args = Bundle()

            fragment.arguments = args

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
}