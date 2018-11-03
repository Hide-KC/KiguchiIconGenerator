package com.development.kc.kiguchiicongenerator.colorpicker

import android.util.Log
import java.lang.Exception

class ColorSubject: Subject<IColorObserver, AHSB>() {
    private var ahsb = AHSB()

    override fun notify(parameter: AHSB) {
        ahsb = parameter
        if (getState() == States.WAIT){
            setState(States.RUNNING)

            try {
                for (observer in observers){
                    observer.colorUpdate(ahsb)
                }
            } catch (e: Exception){
                setState(States.ERROR)
                Log.d(this.javaClass.simpleName, e.message)
                return
            }
            setState(States.WAIT)
        }
    }
}