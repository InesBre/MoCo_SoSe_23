package com.example.barcodebites.feature_Scan.data

import android.content.Context
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.barcodebites.core.data.entities.Product
import com.example.barcodebites.core.inheriting.BaseRepositoryImplementation
import com.example.barcodebites.feature_Scan.domain.http.Request
import com.example.barcodebites.feature_Scan.domain.http.Response
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime


class ScanRepositoryImplementation(val context: Context) : BaseRepositoryImplementation(context) {
    private val api = Request(context)
    private suspend fun <T> apiCall(apiRequest: suspend () -> T): Response<T> {
        return withContext(Dispatchers.IO) {
            try {
                Response.Success(apiRequest.invoke())
            } catch (e: Throwable) {
                val isNetworkError: Boolean = e is VolleyError
                when (e) {
                    is VolleyError -> {
                        val code: Int
                        val message: String
                        if (e.networkResponse == null) {
                            code = 500
                            message = "Connection to service failed (Timeout)"
                        } else {
                            code = e.networkResponse.statusCode
                            message = String(e.networkResponse.data)
                        }
                        Response.Error(isNetworkError, code, message)
                    }
                    else -> {
                        Response.Error(isNetworkError, 500, e.message)
                    }
                }
            }
        }
    }

    suspend fun call(url: String, method: Int) = apiCall {
        api.getObject(url, method)
    }

    data class Ingredients(
        val name:String? = "",
        val vegan: Boolean? = false,
        val vegetarian:Boolean? = false
    )

    fun parseProduct(json: JSONObject): Product? {
        val code = json.getString("code")
        val status = STATUS.values()[json.getInt("status")]
        val data = json.getJSONObject("product")
        if(status === STATUS.NOT_FOUND){
            return reject(code)
        }
        else{
            val fields = getFields()
            val namesList = fields.slice((2..4))
            val name = namesList.find { data.has(it) && it !== "" }?.let { data.getString(it) }
            if(name === null || name === ""){
                return reject(code)
            }
            else{
                Toast.makeText(context, "Produkt '${name}' gefunden!", Toast.LENGTH_SHORT).show()
                val ingredients = mutableListOf<Ingredients>()
                val prodMap: Map<String, Any?> = fields.drop(5).filter { data.has(it) }.map { field ->
                    val value: Any?
                    when(field){
                        fields[5] -> {
                            value = data.getJSONArray(fields[5]).join(" - ")
                        }
                        fields[6] -> {
                            value = (toStrList(data.getJSONArray(fields[6]))).joinToString(",") {
                                it.split(":")[1]
                            }
                        }
                        fields[7] -> value = data.getInt(fields[7]) == 1
                        fields[8] -> value = data.getString(fields[8]) ?: ""
                        fields[9] -> {
                            value = ingredients.addAll((toObjList(data.getJSONArray(fields[9]))).map {
                                val validValues = listOf("yes",",maybe")
                                Ingredients(
                                    name = if(it.has("text")) it.getString("text") else null,
                                    vegan = it.has(fields[1]) && validValues.contains(it.getString(fields[1])),
                                    vegetarian = it.has(fields[2]) && validValues.contains(it.getString(fields[2]))
                                )
                            }.filter { it.name !== "" })
                        }
                        else -> value = null
                    }
                    field to value
                }.toMap()
                return Product(
                    code = code,
                    productName = name,
                    brand = prodMap.getOrDefault(fields[5],"") as String,
                    ingredients = ingredients.joinToString(",") { it.name!! },
                    isVegetarian = ingredients.all { it.vegetarian!! },
                    isVegan = ingredients.all { it.vegan!! },
                    allergens = prodMap.getOrDefault(fields[6],"") as String,
                    complete = prodMap.getOrDefault(fields[7],false) as Boolean,
                    score = prodMap.getOrDefault(fields[8],"") as String,
                    lastScanned = LocalDateTime.now().toString(),
                    userEmail = getUser()!!
                )
            }
        }
    }

    private fun toObjList(input: JSONArray): MutableList<JSONObject> {
        val list = mutableListOf<JSONObject>()
        for (i in 0 until input.length()) {
            list.add(input.getJSONObject(i))
        }
        return list
    }

    private fun toStrList(input: JSONArray): MutableList<String> {
        val list = mutableListOf<String>()
        for (i in 0 until input.length()) {
            list.add(input.getString(i))
        }
        return list
    }

    fun getFields(): List<String> {
        return listOf(
            "vegan",
            "vegetarian",
            "product_name_de",
            "product_name",
            "product_name_en",
            "brands_tags",
            "allergens_tags",
            "complete",
            "nutriscore_grade",
            "ingredients"
        )
    }

    private fun reject(code: String): Product? {
        Toast.makeText(context, "Kein Produkt mit Barcode $code", Toast.LENGTH_SHORT).show()
        return null
    }

    private enum class STATUS{ NOT_FOUND, FOUND }
}