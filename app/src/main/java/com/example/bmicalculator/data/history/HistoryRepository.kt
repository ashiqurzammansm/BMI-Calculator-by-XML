package com.example.bmicalculator.data.history

import com.example.bmicalculator.ui.history.HistoryStorage

class HistoryRepository(private val storage: HistoryStorage) {

    fun save(item: HistoryItem) = storage.save(item)
    fun getHistory(): List<HistoryItem> = storage.getAll()
    fun clear() = storage.clear()
}