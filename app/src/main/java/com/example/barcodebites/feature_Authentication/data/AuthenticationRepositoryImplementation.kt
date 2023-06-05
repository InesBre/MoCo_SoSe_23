package com.example.barcodebites.feature_Authentication.data

import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.feature_Authentication.domain.repository.LoginRepository

class LoginRepositoryImplementation(private val dao: AuthenticationDao): LoginRepository {
    override suspend fun addUser(user: User) {
        dao.addUser(user)
    }

    override suspend fun getPW(email: String): String {
        return dao.getPw(email)
    }

}