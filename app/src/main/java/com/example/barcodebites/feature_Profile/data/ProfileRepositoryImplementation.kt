package com.example.barcodebites.feature_Profile.data



import android.content.Context
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await


class ProfileRepositoryImplementation(context: Context) : BaseRepositoryImplementation(context) {
    suspend fun insertPreference(preference: Preference) {
        db.collection(PREFERENCES_COLLECTION).document(getId(preference)).set(preference).await()
    }

    suspend fun deletePreference(email: String, prefName: String) {
        val preference = Preference(preferenceName = prefName, userEmail = email)
        db.collection(PREFERENCES_COLLECTION).document(getId(preference)).delete().await()
    }
}