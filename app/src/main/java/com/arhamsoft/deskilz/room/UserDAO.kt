package com.arhamsoft.deskilz.room

import androidx.room.*
import com.alphaCareInc.app.room.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Update()
    fun updateUser(user: User)

    @Query("SELECT * FROM user_table WHERE userId =:id")
    fun getPreviousUser(id: String): User?

//    @Query("DELETE FROM user_table")
//    fun deleteUser()

    @Query( "DELETE FROM user_table WHERE id =:id")
    fun deleteUser(id: Int)

//    @Query("SELECT * FROM user_table ")
//    fun getUser(): User?

    @Query("SELECT * FROM user_table WHERE id =:id")
    fun getUser(id: Int): User?

    @Query("SELECT * FROM user_table ")
    fun getUserList(): List<User>

//    @Query("UPDATE user_table SET code = :newCode  WHERE id=:id")
//    fun updateUser(newCode:Int,id:Int)


}