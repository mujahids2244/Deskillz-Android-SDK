package com.arhamsoft.deskilz.domain.listeners

interface NetworkListener<T> {

    fun successFul(t: T)
    fun failure()

}