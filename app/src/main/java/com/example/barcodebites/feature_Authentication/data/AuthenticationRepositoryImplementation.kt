package com.example.barcodebites.feature_Authentication.data

import android.content.Context
import com.example.barcodebites.core.data.BaBiDb
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.feature_Authentication.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImplementation(context: Context) :
    AuthenticationRepository {
    private var dao = BaBiDb.getDatabase(context).authenticationDao()
    override suspend fun addUser(user: User) {
        dao.addUser(user)
    }

    override suspend fun getPW(email: String): String {
        return dao.getPW(email)
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

}