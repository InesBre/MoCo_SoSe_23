package com.example.barcodebites.feature_Authentication.domain.repository

import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.feature_Authentication.data.AuthenticationDao

interface AuthenticationRepository : AuthenticationDao {
    override suspend fun addUser(user: User)
    override suspend fun getPW(email: String): String
}