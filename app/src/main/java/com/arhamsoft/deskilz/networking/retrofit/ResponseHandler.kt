package com.arhamsoft.deskilz.networking.retrofit

interface ResponseHandler<T> {

    fun success(model: T)
    fun failure(error: Any)

}