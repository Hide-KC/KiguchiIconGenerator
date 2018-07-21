package com.development.kc.kiguchiicongenerator

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ControllerFragment: AbsFragment() {
    enum class KeyDirection {
        KEY_UP, KEY_RIGHT, KEY_DOWN, KEY_LEFT
    }

    interface OnKeyClickedListener{
        fun onKeyClicked(keyDirection: KeyDirection)
    }

    private var onKeyClickedListener: OnKeyClickedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnKeyClickedListener){
            onKeyClickedListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.controller_fragment, container, false)


        return view
    }


    companion object {
        fun newInstance(targetFragment: Fragment?, group: IconLayout.GroupEnum): ControllerFragment{
            val args = Bundle()
            args.putInt("group", group.ordinal)
            val fragment = ControllerFragment()
            fragment.arguments = args

            fragment.setTargetFragment(targetFragment, 0)
            return fragment
        }
    }
}