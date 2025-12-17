package com.example.bmicalculator

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    private var gender = "MALE"
    private var height = 170
    private var weight = 60
    private var age = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("BMI_PREF", MODE_PRIVATE)

        // Default gender
        selectMale()

        // Gender selection
        binding.cardMale.setOnClickListener { selectMale() }
        binding.cardFemale.setOnClickListener { selectFemale() }

        // Height
        binding.seekHeight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, p: Int, f: Boolean) {
                height = p.coerceIn(80, 220)
                binding.tvHeight.text = height.toString()
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        // Weight
        binding.btnWeightPlus.setOnClickListener {
            if (weight < 300) {
                weight++
                binding.tvWeight.text = weight.toString()
            }
        }
        binding.btnWeightMinus.setOnClickListener {
            if (weight > 1) {
                weight--
                binding.tvWeight.text = weight.toString()
            }
        }

        // Age
        binding.btnAgePlus.setOnClickListener {
            if (age < 120) {
                age++
                binding.tvAge.text = age.toString()
            }
        }
        binding.btnAgeMinus.setOnClickListener {
            if (age > 1) {
                age--
                binding.tvAge.text = age.toString()
            }
        }

        // Calculate
        binding.btnCalculate.setOnClickListener {
            val bmi = BmiUtils.calculateBMI(weight, height)
            val category = BmiUtils.getCategory(bmi)
            val desc = BmiUtils.getDescription(category, gender)

            prefs.edit()
                .putFloat("LAST_BMI", bmi.toFloat())
                .putString("LAST_CATEGORY", category)
                .apply()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("BMI", bmi)
            intent.putExtra("CATEGORY", category)
            intent.putExtra("DESC", desc)
            intent.putExtra("GENDER", gender)
            intent.putExtra("HEIGHT", height)
            startActivity(intent)
        }
    }

    private fun selectMale() {
        gender = "MALE"
        binding.cardMale.alpha = 1f
        binding.cardFemale.alpha = 0.5f
    }

    private fun selectFemale() {
        gender = "FEMALE"
        binding.cardFemale.alpha = 1f
        binding.cardMale.alpha = 0.5f
    }
}
