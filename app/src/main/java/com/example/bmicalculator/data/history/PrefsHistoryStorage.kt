package com.example.bmicalculator.data.history

import android.content.Context
import com.example.bmicalculator.ui.history.HistoryStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsHistoryStorage(context: Context) : HistoryStorage {

    private val prefs =
        context.getSharedPreferences("bmi_history", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val key = "history_list"

    override fun save(item: HistoryItem) {
        val list = getAll().toMutableList()
        list.add(0, item) // newest first

        prefs.edit()
            .putString(key, gson.toJson(list.take(10)))
            .apply()
    }

    override fun getAll(): List<HistoryItem> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<HistoryItem>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun clear() {
        prefs.edit().remove(key).apply()
    }
}