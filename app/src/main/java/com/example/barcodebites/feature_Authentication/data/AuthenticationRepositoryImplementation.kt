package com.example.barcodebites.feature_Authentication.data

import android.content.Context
import com.example.barcodebites.core.data.BaBiDb
import com.example.barcodebites.core.data.entities.User
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation


class AuthenticationRepositoryImplementation(context: Context) :
    AuthenticationDao, BaseRepositoryImplementation(context) {
    private var dao = BaBiDb.getDatabase(context).authenticationDao()

    override suspend fun getUser(email: String): User {
        return dao.getUser(email)
    }

    override suspend fun addUser(user: User) {
        dao.addUser(user)
    }

    override suspend fun getPW(email: String): String {
        return dao.getPW(email)
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    suspend fun login(email: String, password: String): Boolean {
        val isAuthenticated = password == getPW(email)
        if(isAuthenticated)  setUser(email)
        return isAuthenticated
    }

    fun logout(){ removeUser() }

    fun isLoggedIn(): Boolean {
        return checkUser()
    }
}