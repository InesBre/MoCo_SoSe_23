package com.example.barcodebites.feature_Scan.domain.repository

import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.feature_Scan.data.ScanDao

class ScanRepository : ScanDao {
    override suspend fun getAll(productId: Int): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }
}