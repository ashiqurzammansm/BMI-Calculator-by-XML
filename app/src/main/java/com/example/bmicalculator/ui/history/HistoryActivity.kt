package com.example.bmicalculator.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmicalculator.data.history.HistoryRepository
import com.example.bmicalculator.data.history.PrefsHistoryStorage
import com.example.bmicalculator.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var repo: HistoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = HistoryRepository(PrefsHistoryStorage(this))

        loadHistory()

        binding.btnClear.setOnClickListener {
            repo.clear()
            loadHistory()
        }
    }

    private fun loadHistory() {
        val list = repo.getHistory()
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistory.adapter = HistoryAdapter(list)
    }
}
