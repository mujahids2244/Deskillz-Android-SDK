package com.alphaCareInc.app.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var accessToken: String? = ""
    var userId: String? = ""
    var userName: String? = ""
    var userEmail: String? = ""
//    var code:Int = 0
//    var status: Boolean= false
//    var redirectUrl:String? = ""

}




