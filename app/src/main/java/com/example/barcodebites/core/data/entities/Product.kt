package com.example.barcodebites.core.data.entities

import java.time.LocalDateTime

data class Product(
    val code: String = "",
    val productName: String = "",
    val brand: String = "",
    val ingredients: String = "",
    val allergens: String = "",
    @field:JvmField //Wird benötigt, da Feld mit is beginnt (https://github.com/firebase/snippets-android/blob/8d30a4f468de7c8703023abb7eae7b04df0cf321/firestore/app/src/main/java/com/google/example/firestore/kotlin/DocSnippets.kt#L239-L247)
    val isVegetarian: Boolean = false,
    @field:JvmField
    val isVegan: Boolean = false,
    val complete: Boolean = false,
    val score: String = "",
    @field:JvmField
    var isLiked: Boolean = false,
    val lastScanned: String = "",
    val userEmail: String = ""
)