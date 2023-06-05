package com.example.barcodebites.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int,
    @ColumnInfo(name = "productName") val productName: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "complete") val complete: Boolean,
    @ColumnInfo(name = "score") val score: String,
    @ColumnInfo(name = "isEdible") val isEdible: Boolean? = false,
    @ColumnInfo(name = "isLiked") val isLiked: Boolean? = false
)