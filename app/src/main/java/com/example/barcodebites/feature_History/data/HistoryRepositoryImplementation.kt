package com.example.barcodebites.feature_History.data

import android.content.Context
import android.util.Log
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

//The HistoryRepositoryImplementation class is a repository that provides CRUD operations for Product entities
//stored in a Firebase Firestore database. It extends the BaseRepositoryImplementation class, which provides
//methods for accessing the Firestore database.
//
//The deleteProduct method takes a String id as an argument and deletes the corresponding document from the
//Firestore database. It uses the await method to wait for the deletion to complete before returning.

class HistoryRepositoryImplementation(val context: Context) :
    BaseRepositoryImplementation(context) {
    suspend fun deleteProduct(id: String) {1
        db.collection(PRODUCTS_COLLECTION).document(id).delete().await()
    }
}