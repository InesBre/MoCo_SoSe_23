package com.example.barcodebites.feature_Authentication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.barcodebites.core.data.entities.User

@Dao
interface AuthenticationDao {
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUser(email: String): User

    @Query("SELECT password FROM user WHERE email = :email LIMIT 1")
    suspend fun getPW(email: String): String

    //register
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}