package com.example.barcodebites.feature_Scan.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.barcodebites.core.data.entities.Product

@Dao
interface ScanDao {
    @Query("SELECT * FROM Product WHERE productId = :productId")
    suspend fun getAll(productId: Int): List<Product>

    @Upsert
    suspend fun upsertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)
}