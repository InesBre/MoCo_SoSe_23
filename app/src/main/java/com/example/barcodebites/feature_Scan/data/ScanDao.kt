package com.example.barcodebites.feature_Scan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.barcodebites.core.data.entities.Product

@Dao
interface ScanDao {
    @Query("SELECT * FROM Product WHERE productId = :productId")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT * FROM Product WHERE productName = :productName")
    suspend fun getProductByName(productName: String): Product?

    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)
}