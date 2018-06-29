package com.development.kc.kiguchiicongenerator

import android.support.v4.app.Fragment

abstract class AbsFragment: Fragment() {

    interface FragmentListener {
        fun onFragmentEvent(events: Events)
    }

    protected var mListener: FragmentListener? = null


}