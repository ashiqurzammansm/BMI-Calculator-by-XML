package com.example.bmicalculator

import android.content.Intent
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
        val gender = intent.getStringExtra("GENDER") ?: "MALE"
        val height = intent.getIntExtra("HEIGHT", 170)

        binding.tvBmiValue.text = String.format("%.1f", bmi)
        binding.tvBmiCategory.text = category
        binding.tvBmiDesc.text = desc
        binding.tvIdealRange.text =
            "Ideal weight range: ${BmiUtils.idealWeightRange(height)}"

        binding.tvBmiCategory.setTextColor(
            BmiUtils.getCategoryColor(category)
        )

        // Share
        binding.btnShare.setOnClickListener {
            val shareText =
                "BMI Result\nBMI: %.1f\nCategory: %s\nGender: %s"
                    .format(bmi, category, gender)

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        binding.btnRecalculate.setOnClickListener {
            finish()
        }
    }
}
