package com.development.kc.kiguchiicongenerator

class ColorSelectSubject: Subject() {
    override fun notifyColorChange(hue: Float, saturation: Float, brightness: Float) {
        if (getState() == States.WAIT){
            setState(States.RUNNING)
            for (o in observers){
                try {
                    o.colorUpdate(hue, saturation, brightness)
                } catch (e: Exception){
                    setState(States.ERROR)
                    return
                }
            }
            setState(States.WAIT)
        } else if (getState() == States.ERROR) {

        }
    }
}