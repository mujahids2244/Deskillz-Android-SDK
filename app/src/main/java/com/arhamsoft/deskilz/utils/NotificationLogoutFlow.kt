package com.arhamsoft.deskilz.utils

interface LogoutInterface {
    fun logoutListener()
}

object LogoutHandler {
    lateinit var logout:LogoutInterface

    fun setListener (logoutInterface: LogoutInterface){
                logout = logoutInterface
    }

    fun triggerInterface() {
        logout.logoutListener()
    }

}
