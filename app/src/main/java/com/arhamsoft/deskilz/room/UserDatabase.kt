package com.alphaCareInc.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arhamsoft.deskilz.room.UserDAO

@Database(entities = [User::class], version = 1, exportSchema = false )
//@TypeConverters(Converters::class)
abstract   class UserDatabase: RoomDatabase() {


   abstract fun userDao(): UserDAO

   companion object{
      @Volatile

      private  var INSTANACE: UserDatabase? = null

      fun getDatabase(context: Context): UserDatabase
      {
         if(INSTANACE == null){
            synchronized(this){

               INSTANACE = Room.databaseBuilder(
                  context.applicationContext,
                  UserDatabase::class.java,
                  "UserDB"
               ).allowMainThreadQueries().build()
            }


         }
         return INSTANACE!!
      }


   }


}