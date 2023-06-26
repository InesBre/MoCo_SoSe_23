package com.example.barcodebites.feature_Profile.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.barcodebites.core.data.entities.Preference

@Dao
interface ProfileDao {
    @Query("SELECT p.* FROM user u JOIN preference p ON u.email = p.userEmail WHERE email = :email")
    suspend fun getPreferences(email: String): List<Preference>

    @Insert
    suspend fun insertPreference(preference: Preference)

    @Query("DELETE FROM preference WHERE userEmail = :email AND preferenceName = :prefName")
    suspend fun deletePreference(email: String, prefName: String)
}