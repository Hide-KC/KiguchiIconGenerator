package com.development.kc.kiguchiicongenerator

abstract class Subject {
    enum class States {
        WAIT, RUNNING, ERROR
    }

    protected val observers = mutableListOf<IObserver>()
    private var state: States = States.WAIT

    fun attach(observer: IObserver) {
        observers.add(observer)
    }

    fun detach(observer: IObserver) {
        for(i in observers.indices){
            //参照先の比較
            if (observers[i] == observer){
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