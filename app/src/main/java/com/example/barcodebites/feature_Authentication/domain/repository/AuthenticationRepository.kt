package com.example.barcodebites.feature_Authentication.domain.repository

import com.example.barcodebites.core.data.entities.User

interface LoginRepository {
    suspend fun addUser(user: User)
    suspend fun getPW(email: String): String
}