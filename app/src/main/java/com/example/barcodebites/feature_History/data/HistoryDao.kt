package com.example.barcodebites.feature_History.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.barcodebites.core.data.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM Product WHERE userEmail = :email")
    fun getAll(email:String): Flow<List<Product>>

    @Update
    suspend fun updateProduct(product: Product)

    @Query("DELETE FROM Product WHERE productId = :id")
    suspend fun deleteProduct(id:Int)
}