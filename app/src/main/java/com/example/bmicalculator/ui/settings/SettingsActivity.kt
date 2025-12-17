package com.example.bmicalculator.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.data.history.HistoryRepository
import com.example.bmicalculator.data.history.PrefsHistoryStorage
import com.example.bmicalculator.data.settings.SettingsPrefs
import com.example.bmicalculator.data.settings.UnitType
import com.example.bmicalculator.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings = SettingsPrefs(this)
        val repo = HistoryRepository(PrefsHistoryStorage(this))

        binding.switchUnit.isChecked =
            settings.getUnit() == UnitType.IMPERIAL

        binding.switchUnit.setOnCheckedChangeListener { _, checked ->
            settings.setUnit(
                if (checked) UnitType.IMPERIAL else UnitType.METRIC
            )
        }

        binding.btnClearHistory.setOnClickListener {
            repo.clear()
        }
    }
}
