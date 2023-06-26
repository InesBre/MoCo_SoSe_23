
package com.example.barcodebites.core.inheriting

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.core.data.entities.Product

abstract class BaseRepositoryImplementation(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("com.example.barcodeBites",
        Context.MODE_PRIVATE)
    private val key = "loggedIn"

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
        "isSoy" to Triple("Sojafreie Ernährung","Alles was Soja enthält ist raus") {
            return@Triple it.allergens.contains("soy")
        }
    )

    fun filterProducts(pref: Preference,product: Product){
        val str = "${pref.preferenceName}: ${product.productName}"
        Log.d("FILTER",str)
    }

    fun setUser(email: String){
        prefs.edit().putString(key,email).apply()
    }

    fun checkUser(): Boolean {
        return prefs.getString(key,null) != null
    }

    fun getUser(): String? {
        return prefs.getString(key,null)
    }

    fun removeUser(){
        prefs.edit().remove(key).apply()
    }
}