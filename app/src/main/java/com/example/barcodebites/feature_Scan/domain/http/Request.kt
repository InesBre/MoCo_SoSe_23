package com.example.barcodebites.feature_Scan.domain.http

import android.content.Context
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Request(val context: Context?) {
    private val baseURL = "https://world.openfoodfacts.org/api/v2/product"
    private val queue = Volley.newRequestQueue(context)

    suspend fun getArray(
        url: String,
        method: Int = Request.Method.GET,
        payload: JSONArray? = null
    ) = suspendCoroutine<List<JSONObject>> { cont ->

        queueRequest(JsonArrayRequest(
            method,
            listOf(baseURL,url).joinToString("/"),
            payload,
            { response -> cont.resume(toList(response)) },
            { error: VolleyError? -> cont.resumeWithException(error as Throwable) }
        ))
    }

    suspend fun getObject(
        url: String,
        method: Int = Request.Method.GET,
        payload: JSONObject? = null
    ) = suspendCoroutine<JSONObject> { cont ->
        queueRequest(JsonObjectRequest(
            method,
            listOf(baseURL,url).joinToString("/"),
            payload,
            { response ->
                cont.resume(response)
            },
            { error: VolleyError? -> cont.resumeWithException(error as Throwable) }
        ))
    }

    fun toList(input: JSONArray): MutableList<JSONObject> {
        val list = mutableListOf<JSONObject>()
        for (i in 0 until input.length()) {
            list.add(input.getJSONObject(i))
        }
        return list
    }

    private fun queueRequest(request: StringRequest) {
        queue.add(request)
    }

    private fun queueRequest(request: JsonArrayRequest) {
        queue.add(request)
    }

    private fun queueRequest(request: JsonObjectRequest) {
        queue.add(request)
    }

    /*fun getHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["User-Agent"] = "Barcode-Bites - Version 1.0.0"
        return headers
    }*/
}