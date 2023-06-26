package com.example.barcodebites.feature_History.data

import android.content.Context
import com.example.barcodebites.core.data.BaBiDb
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


class HistoryRepositoryImplementation(val context: Context) : HistoryDao, BaseRepositoryImplementation(context) {
    private val dao = BaBiDb.getDatabase(context).historyDao()

    override fun getAll(email: String): Flow<List<Product>> {
        return dao.getAll(email)
    }

    override suspend fun deleteProduct(id: Int){
        dao.deleteProduct(id)
    }

    override suspend fun updateProduct(product: Product) {
        dao.updateProduct(product)
    }

    fun getAllProducts(): Flow<List<Product>> {
        return when(val email = getUser()){
            null -> emptyFlow()
            else -> getAll(email)
        }
    }
}