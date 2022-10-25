package com.arhamsoft.websocket

interface MessageInterface {
    fun  onConnectSuccess () // successfully connected
    fun  onConnectFailed () // connection failed
    fun  onClose () // close
    fun onMessage(text: String?)
}