package com.example.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bmi = intent.getDoubleExtra("BMI", 0.0)
        val category = intent.getStringExtra("CATEGORY") ?: ""
        val desc = intent.getStringExtra("DESC") ?: ""

        binding.tvBMI.text = String.format("%.1f", bmi)
        binding.tvCategory.text = category
        binding.tvDesc.text = desc
        binding.tvCategory.setTextColor(BmiUtils.getCategoryColor(category))

        binding.btnRecalculate.setOnClickListener {
            finish()
        }
    }
}
