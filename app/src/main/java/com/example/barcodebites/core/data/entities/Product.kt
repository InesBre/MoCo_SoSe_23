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
    @ColumnInfo(name = "allergens") val allergens: String,
    @ColumnInfo(name = "isVegetarian") val isVegetarian: Boolean,
    @ColumnInfo(name = "isVegan") val isVegan: Boolean,
    @ColumnInfo(name = "complete") val complete: Boolean,
    @ColumnInfo(name = "score") val score: String,
    @ColumnInfo(name = "isEdible") val isEdible: Boolean = false,
    @ColumnInfo(name = "isLiked") var isLiked: Boolean = false,
    @ColumnInfo(name = "lastScanned") val lastScanned: String,
    @ColumnInfo(name = "userEmail") val userEmail: String
)