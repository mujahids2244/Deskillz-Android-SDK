package com.arhamsoft.deskilz.utils

import android.content.Context
import android.content.SharedPreferences
import com.alphaCareInc.app.room.User
import com.google.gson.Gson

class CustomSharedPreference(context: Context) {
    private val PREFS_NAME = "Deskillz"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPref.edit()


    fun saveValue(KEY_NAME: String, value: String) {

        editor.putString(KEY_NAME, value)

        editor.commit()
    }

    fun saveUserObj(KEY:String? , obj: User)
    {


        val gson = Gson()

        val json = gson.toJson(obj)

        editor.putString(KEY,json)
        editor.commit()


    }

    fun saveCurrentLoginID(KEY_NAME: String, value: Int){

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }



    fun returnValue(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, "")
    }

    fun returnCurrentLoginID(KEY_NAME: String): Int? {

        return sharedPref.getInt(KEY_NAME, 0)
    }




    fun saveLogin(KEY_NAME: String, value: Boolean) {

        editor.putBoolean(KEY_NAME, value)

        editor.commit()
    }

    fun saveCodeCheck(KEY_NAME: String, value: Boolean) {

        editor.putBoolean(KEY_NAME, value)

        editor.commit()
    }

    fun isLogin(KEY_NAME: String): Boolean {

        return sharedPref.getBoolean(KEY_NAME, false)
    }


    fun isCodeChecked(KEY_NAME: String): Boolean {

        return sharedPref.getBoolean(KEY_NAME, false)
    }





//
    fun clearSharedPreference() {

        editor.clear()
        editor.commit()
    }

////    fun saveLoginData(KEY_NAME: String, obj: User) {
////
////        val dsave = sharedPref.edit()
////
////        val gson = Gson()
////
////        val json = gson.toJson(obj)
////
////        dsave.putString(KEY_NAME,json)
////        dsave.apply()
////    }
//
}