package com.arhamsoft.deskilz.networking.retrofit

import android.util.Log
import retrofit2.Response

class CallApi {

    fun <T> callApi(
        retrofitClient: Response<T>,
        listener: ResponseHandler<T>
    ) {
        try {
            if (retrofitClient.isSuccessful) {
                retrofitClient.body()?.let { listener.success(it) }
            } else {
                retrofitClient.errorBody()?.let { listener.failure(it) }
            }
        }
        catch (e:Exception) {
            Log.e("API EXCEPTION", "callApi:$e " )
            listener.failure(e)
        }
    }

    companion object {
        private var instance: CallApi? = null

        fun getInstance(): CallApi {
            if (instance == null) {
                instance = CallApi()
            }
           return instance!!
        }
    }

}