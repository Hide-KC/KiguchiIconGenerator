package com.development.kc.kiguchiicongenerator

class ColorChangeSubject: Subject() {
    override fun notifyColorChange(hue: Float, saturation: Float, brightness: Float) {
        for (o in observers){
            o.colorUpdate(hue, saturation, brightness)
        }
    }
}