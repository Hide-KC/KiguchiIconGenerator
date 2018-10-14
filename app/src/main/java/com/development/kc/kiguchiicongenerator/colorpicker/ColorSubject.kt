package com.development.kc.kiguchiicongenerator.colorpicker

import com.development.kc.kiguchiicongenerator.colorpicker.AHSB
import com.development.kc.kiguchiicongenerator.colorpicker.IColorObserver

class ColorSubject: Subject<IColorObserver, AHSB>() {
    private var ahsb = AHSB()

    override fun notify(parameter: AHSB) {
        ahsb = parameter
        for (observer in observers){
            observer.colorUpdate(ahsb)
        }
    }
}