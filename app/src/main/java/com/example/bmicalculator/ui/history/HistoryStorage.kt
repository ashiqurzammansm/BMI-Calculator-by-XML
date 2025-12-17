package com.example.bmicalculator.ui.history

import com.example.bmicalculator.data.history.HistoryItem

interface HistoryStorage {
    fun save(item: HistoryItem)
    fun getAll(): List<HistoryItem>
    fun clear()
}
