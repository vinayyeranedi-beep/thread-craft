package com.example.threadcraft.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SavedQuote(
    val id: String,
    val date: String,
    val colorId: String,
    val colorLabel: String,
    val colorHex: String,
    val designId: String,
    val designLabel: String,
    val size: String,
    val quantity: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val discountPct: Int,
    val view: String
)

class QuoteRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("threadcraft_quotes", Context.MODE_PRIVATE)
    private val _quotes = MutableStateFlow<List<SavedQuote>>(emptyList())
    val quotes: StateFlow<List<SavedQuote>> = _quotes.asStateFlow()

    init {
        loadQuotes()
    }

    private fun loadQuotes() {
        val jsonString = prefs.getString("saved_quotes_json", null)
        if (jsonString.isNullOrBlank()) {
            // Seed with one initial sample order so the user sees how quotes look
            val seed = listOf(
                SavedQuote(
                    id = "tc-sample-1",
                    date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
                    colorId = "black",
                    colorLabel = "Black",
                    colorHex = "#111114",
                    designId = "vinayaka-chavithi",
                    designLabel = "Vinayaka Chavithi",
                    size = "L",
                    quantity = 25,
                    unitPrice = 449,
                    totalPrice = 11225,
                    discountPct = 10,
                    view = "front"
                )
            )
            _quotes.value = seed
            saveQuotesToPrefs(seed)
            return
        }

        try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<SavedQuote>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    SavedQuote(
                        id = obj.getString("id"),
                        date = obj.optString("date", ""),
                        colorId = obj.getString("colorId"),
                        colorLabel = obj.getString("colorLabel"),
                        colorHex = obj.getString("colorHex"),
                        designId = obj.getString("designId"),
                        designLabel = obj.getString("designLabel"),
                        size = obj.getString("size"),
                        quantity = obj.getInt("quantity"),
                        unitPrice = obj.getInt("unitPrice"),
                        totalPrice = obj.getInt("totalPrice"),
                        discountPct = obj.optInt("discountPct", 0),
                        view = obj.optString("view", "front")
                    )
                )
            }
            _quotes.value = list
        } catch (_: Exception) {
            _quotes.value = emptyList()
        }
    }

    fun saveQuote(quote: SavedQuote) {
        val updated = listOf(quote) + _quotes.value.filter { it.id != quote.id }
        _quotes.value = updated
        saveQuotesToPrefs(updated)
    }

    fun deleteQuote(id: String) {
        val updated = _quotes.value.filter { it.id != id }
        _quotes.value = updated
        saveQuotesToPrefs(updated)
    }

    private fun saveQuotesToPrefs(list: List<SavedQuote>) {
        val jsonArray = JSONArray()
        for (q in list) {
            val obj = JSONObject().apply {
                put("id", q.id)
                put("date", q.date)
                put("colorId", q.colorId)
                put("colorLabel", q.colorLabel)
                put("colorHex", q.colorHex)
                put("designId", q.designId)
                put("designLabel", q.designLabel)
                put("size", q.size)
                put("quantity", q.quantity)
                put("unitPrice", q.unitPrice)
                put("totalPrice", q.totalPrice)
                put("discountPct", q.discountPct)
                put("view", q.view)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString("saved_quotes_json", jsonArray.toString()).apply()
    }
}
