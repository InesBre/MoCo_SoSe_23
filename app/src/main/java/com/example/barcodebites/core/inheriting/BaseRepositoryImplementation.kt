package com.example.barcodebites.core.inheriting

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.data.entities.Product
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.AssertionError

abstract class BaseRepositoryImplementation(context: Context) {
    val db = Firebase.firestore
    val auth = Firebase.auth
    val PRODUCTS_COLLECTION = "products"
    val PREFERENCES_COLLECTION = "preferences"

    private val key = "loggedIn"
    private val prefs: SharedPreferences = context.getSharedPreferences("com.example.barcodeBites",
        Context.MODE_PRIVATE)

    val preferencesList: Map<String,Triple<String,String?,(product: Product) -> Boolean>> = mapOf(
        "isVegan" to Triple("Vegane Ernährung",null) {
            return@Triple it.isVegan
        },
        "isVegetarian" to Triple("Vegetarische Ernährung",null) {
            return@Triple it.isVegetarian
        },
        "isNuts" to Triple("Nussfreie Ernährung",null) {
            return@Triple it.allergens.contains("nut")
        },
        "isSoy" to Triple("Sojafreie Ernährung",null) {
            return@Triple it.allergens.contains("soy")
        },
        "isFish" to Triple("Fischfreie Ernährung",null) {
            return@Triple it.allergens.contains("fish")
        },
        "isCrustaceans" to Triple("Krebstierfreie Ernährung",null) {
            return@Triple it.allergens.contains("crustaceans")
        },
        "isSellerie" to Triple("Selleriefreie Ernährung",null) {
            return@Triple it.allergens.contains("sellerie")
        },
        "isMilk" to Triple("Milchfreie Ernährung",null) {
            return@Triple it.allergens.contains("milk")
        },
        "isGluten" to Triple("Glutenfreie Ernährung",null) {
            return@Triple it.allergens.contains("gluten")
        },
        "isEggs" to Triple("Eifreie Ernährung",null) {
            return@Triple it.allergens.contains("eggs")
        },
        "isPeanuts" to Triple("Erdnussfreie Ernährung",null) {
            return@Triple it.allergens.contains("peanuts")
        },
        "isLupin" to Triple("Lupinfreie Ernährung",null) {
            return@Triple it.allergens.contains("lupin")
        },
        "isMustard" to Triple("Senffreie Ernährung",null) {
            return@Triple it.allergens.contains("mustard")
        },
        "isSesamSeeds" to Triple("Sesamfreie Ernährung",null) {
            return@Triple it.allergens.contains("sesame-seeds")
        },
        "isSulfur" to Triple("Schwefeldioxidfreie Ernährung",null) {
            return@Triple it.allergens.contains("sulfur-dioxide")
        },
        "isSulfid" to Triple("Sulfidfreie Ernährung",null) {
            return@Triple it.allergens.contains("sulfiting-agents")
        }
    )

    suspend fun checkNonEdibility(product: Product): Boolean {
        return getUserPreferences(getUser()!!).any{
            val preference = preferencesList[it.preferenceName]
            preference!!.third.invoke(product)
        }
    }

    suspend fun getUserPreferences(email: String): List<Preference> {
        return db.collection(PREFERENCES_COLLECTION).whereEqualTo("userEmail",email).get().await().toObjects()
    }

    fun checkUser(): Boolean {
        return auth.currentUser !== null
    }

    fun getId(data: Any): String {
        return when(data){
            is Product -> "${data.code}:${data.userEmail}"
            is Preference -> "${data.preferenceName}:${data.userEmail}"
            else -> throw AssertionError("Expected either Product or Preference Datatype, Received: ${data.javaClass.kotlin}")
        }
    }

    suspend fun upsertProduct(product: Product) {
        db.collection(PRODUCTS_COLLECTION).document(getId(product)).set(product, SetOptions.merge()).await()
    }

    fun getUser(): String? {
        return auth.currentUser?.email
    }

    fun removeUser(){
        auth.signOut()
    }
}
