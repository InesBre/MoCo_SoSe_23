package com.example.barcodebites.feature_Profile.data


import android.content.Context
import com.example.barcodebites.core.data.BaBiDb
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation


class ProfileRepositoryImplementation(context: Context) : ProfileDao, BaseRepositoryImplementation(context) {
    private val dao = BaBiDb.getDatabase(context).profileDao()
    override suspend fun getPreferences(email: String): List<Preference> {
        return dao.getPreferences(email)
    }

    override suspend fun insertPreference(preference: Preference) {
        dao.insertPreference(preference)
    }

    override suspend fun deletePreference(email: String, prefName: String) {
        dao.deletePreference(email, prefName)
    }
}