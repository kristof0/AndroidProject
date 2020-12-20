package com.wannaeat.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//user table for profiles
@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true) val id:Int,
    val name:String,
    val address:String,
    val phone:String,
    val email:String,
    val password:String
)