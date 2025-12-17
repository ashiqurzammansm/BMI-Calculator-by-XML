package com.example.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener {

            val heightCm = binding.etHeight.text.toString()
            val weightKg = binding.etWeight.text.toString()

            if (heightCm.isNotEmpty() && weightKg.isNotEmpty()) {

                val heightMeter = heightCm.toDouble() / 100
                val weight = weightKg.toDouble()

                val bmi = weight / (heightMeter * heightMeter)

                binding.tvResult.text = "Your BMI: %.2f".format(bmi)
            } else {
                binding.tvResult.text = "Please enter height & weight"
            }
        }
    }
}
