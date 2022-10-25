package com.arhamsoft.deskilz.networking.retrofit

import android.util.Log
import com.alphaCareInc.app.room.User
import com.alphaCareInc.app.room.UserDatabase
import com.arhamsoft.deskilz.utils.CustomSharedPreference
import com.arhamsoft.deskilz.AppController
import com.arhamsoft.deskilz.networking.networkModels.ErrorModel
import com.arhamsoft.deskilz.utils.StaticFields
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {
    lateinit var sharedPreference: CustomSharedPreference
//    var obj3:User = User()
    var userLogin: User? = null

    private var timeOut: Long = 30
    val apiClient: ApiListCalling
        get() {
            val retrofit = createRetrofit()
            return retrofit.create(ApiListCalling::class.java)
        }

    private fun createOkHttpClient(accessToken: String): OkHttpClient {
        val bearerToken = "Bearer $accessToken"

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.readTimeout(timeOut, TimeUnit.SECONDS)
        builder.connectTimeout(timeOut, TimeUnit.SECONDS)
        builder.writeTimeout(timeOut, TimeUnit.SECONDS)
        builder.addInterceptor(interceptor)

        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("DeSkillzSdkKey", StaticFields.key)
                .addHeader("Authorization", bearerToken)
                .addHeader("x-auth-token", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAwMTIwMTcsImlhdCI6MTY0ODQ3NjAxNywic3ViIjoiNjI0MWJkMWVjNzc4NGU3MDE1YjA4ZDE3In0.cMWXoq3n8Oaz2DjiKu54t2cwGQiPF5dVWklZstn1PNI")
                .build()
                chain.proceed(request)

        }

        builder.addInterceptor(Interceptor { chain ->
            val request = chain.request()
            try {
                return@Interceptor chain.proceed(request)
            }catch (ex: Exception) {
                Log.e("InternetError", ex.toString());
            }
            return@Interceptor okhttp3.Response.Builder().code(400).body("Error".toResponseBody())
                .protocol(Protocol.HTTP_2).message("Time out").request(request).build()
        })

        return builder.build()
    }

    private fun createRetrofit(): Retrofit {

        sharedPreference= CustomSharedPreference(AppController.getContext())
//        val accessToken: String = if (StaticFields.userModel != null)
//            StaticFields.userModel!!.accessToken!!
//        else ""

//        val gson = Gson()
        val id = sharedPreference.returnCurrentLoginID("user")
//        if(gson.fromJson(json, User::class.java) != null){
//        obj3 = gson.fromJson(json, User::class.java)
//        }

        userLogin = UserDatabase.getDatabase(
            AppController.getContext()
        ).userDao().getUser(id!!)


//        val gson = GsonBuilder()
//            .setLenient()
//            .create()

        return Retrofit.Builder()
            .baseUrl(URLConstant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient(userLogin?.accessToken ?: ""))
            .build()
    }

    companion object {
        private var instance: RetrofitClient? = null

        fun getInstance(): ApiListCalling {
            if (instance == null) {
                instance = RetrofitClient()
            }
            return instance!!.apiClient
        }

        fun updateInstance(): ApiListCalling {
            instance = RetrofitClient()
            return instance!!.apiClient
        }

    }

}