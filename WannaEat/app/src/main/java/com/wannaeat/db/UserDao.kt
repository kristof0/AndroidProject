package com.wannaeat.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wannaeat.model.User
import retrofit2.http.QueryName


interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE (email LIKE :queryName) AND (password LIKE :queryPassw) ")
    fun getUser(queryName: String , queryPassw:String):User
}