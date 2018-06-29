package com.development.kc.kiguchiicongenerator

import android.content.Context

enum class Events {
    PARTS_GRID_EVENT {
        override fun getInt(context: Context): Int {return 0}

        override fun apply(context: Context){

        }
    },

    CONTROLLER_EVENT {
        override fun getInt(context: Context): Int {return 0}

        override fun apply(context: Context) {

        }
    };

    abstract fun apply(context: Context)
    abstract fun getInt(context: Context): Int
}