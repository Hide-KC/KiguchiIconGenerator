package com.development.kc.kiguchiicongenerator

interface IObserver {
    fun colorUpdate(hue: Float, saturation: Float, brightness: Float)
}