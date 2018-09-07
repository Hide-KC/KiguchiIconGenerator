package com.development.kc.kiguchiicongenerator

abstract class Subject {
    enum class States {
        WAIT, RUNNING, ERROR
    }

    protected val observers = mutableListOf<IColorObserver>()
    private var state: States = States.WAIT

    fun attach(colorObserver: IColorObserver) {
        observers.add(colorObserver)
    }

    fun detach(colorObserver: IColorObserver) {
        for(i in observers.indices){
            //参照先の比較
            if (observers[i] == colorObserver){
                observers.removeAt(i)
                return
            }
        }
    }

    //Subject#notifyは異なる引数を作る可能性がるため抽象化
    abstract fun notifyColorChange(hue: Float, saturation: Float, brightness: Float)

    protected fun getState(): States{
        return state
    }

    protected fun setState(state: States) {
        this.state = state
    }

    fun detachAll(){
        observers.clear()
    }
}