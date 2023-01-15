package ru.touchthegrass.tpc.data.local

abstract class LocalProvider {

    private var id = 1
    protected fun getId() = id++
}