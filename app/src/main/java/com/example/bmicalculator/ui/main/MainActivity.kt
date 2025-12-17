package com.example.bmicalculator.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.R
import com.example.bmicalculator.data.settings.SettingsPrefs
import com.example.bmicalculator.data.settings.UnitType
import com.example.bmicalculator.databinding.ActivityMainBinding
import com.example.bmicalculator.ui.result.ResultActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var settings: SettingsPrefs
    private var unitType = UnitType.METRIC

    private var gender = "MALE"
    private var heightCm = 170
    private var weightKg = 60
    private var age = 25

    private val MIN_WEIGHT = 1
    private val MAX_WEIGHT = 300
    private val MIN_AGE = 1
    private val MAX_AGE = 120

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settings = SettingsPrefs(this)
        unitType = settings.getUnit()

        window.decorView.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_scale)
        )

        setupUnitSwitch()
        setupGender()
        setupHeight()
        setupWeight()
        setupAge()
        setupButtonEffect()
        setupCalculate()
    }

    /* ---------------- Unit Switch ---------------- */

    private fun setupUnitSwitch() {
        binding.switchUnit.isChecked = unitType == UnitType.IMPERIAL
        updateUnitUI()

        binding.switchUnit.setOnCheckedChangeListener { _, checked ->
            unitType = if (checked) UnitType.IMPERIAL else UnitType.METRIC
            settings.setUnit(unitType)
            updateUnitUI()
        }
    }

    private fun updateUnitUI() {
        if (unitType == UnitType.METRIC) {
            binding.tvHeight.text = heightCm.toString()
            binding.tvWeight.text = weightKg.toString()
            binding.sliderHeight.value = heightCm.toFloat()
        } else {
            binding.tvHeight.text = cmToFt(heightCm).format(1)
            binding.tvWeight.text = kgToLb(weightKg).toInt().toString()
            binding.sliderHeight.value = heightCm.toFloat()
        }
    }

    /* ---------------- Gender ---------------- */

    private fun setupGender() {
        binding.chipMale.isChecked = true
        binding.chipMale.setOnCheckedChangeListener { _, c -> if (c) gender = "MALE" }
        binding.chipFemale.setOnCheckedChangeListener { _, c -> if (c) gender = "FEMALE" }
    }

    /* ---------------- Height ---------------- */

    private fun setupHeight() {
        binding.sliderHeight.addOnChangeListener { _, value, _ ->
            heightCm = value.toInt()
            binding.tvHeight.text =
                if (unitType == UnitType.METRIC)
                    heightCm.toString()
                else
                    cmToFt(heightCm).format(1)
        }
    }

    /* ---------------- Weight ---------------- */

    private fun setupWeight() {
        updateWeightButtons()

        binding.btnWeightPlus.setOnClickListener {
            if (weightKg < MAX_WEIGHT) {
                weightKg++
                updateUnitUI()
                updateWeightButtons()
            }
        }

        binding.btnWeightMinus.setOnClickListener {
            if (weightKg > MIN_WEIGHT) {
                weightKg--
                updateUnitUI()
                updateWeightButtons()
            }
        }
    }

    private fun updateWeightButtons() {
        binding.btnWeightMinus.isEnabled = weightKg > MIN_WEIGHT
        binding.btnWeightPlus.isEnabled = weightKg < MAX_WEIGHT
    }

    /* ---------------- Age ---------------- */

    private fun setupAge() {
        updateAgeButtons()

        binding.btnAgePlus.setOnClickListener {
            if (age < MAX_AGE) {
                age++
                binding.tvAge.text = age.toString()
                updateAgeButtons()
            }
        }

        binding.btnAgeMinus.setOnClickListener {
            if (age > MIN_AGE) {
                age--
                binding.tvAge.text = age.toString()
                updateAgeButtons()
            }
        }
    }

    private fun updateAgeButtons() {
        binding.btnAgeMinus.isEnabled = age > MIN_AGE
        binding.btnAgePlus.isEnabled = age < MAX_AGE
    }

    /* ---------------- Calculate ---------------- */

    private fun setupCalculate() {
        binding.btnCalculate.setOnClickListener {
            val result = viewModel.calculateBMI(weightKg, heightCm, gender)
            startActivity(
                Intent(this, ResultActivity::class.java)
                    .putExtra("BMI_RESULT", result)
            )
        }
    }

    /* ---------------- UX ---------------- */

    private fun setupButtonEffect() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.button_press)
        binding.btnCalculate.setOnTouchListener { v, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN -> v.startAnimation(anim)
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> v.clearAnimation()
            }
            false
        }
    }

    /* ---------------- Helpers ---------------- */

    private fun kgToLb(kg: Int) = kg * 2.20462
    private fun cmToFt(cm: Int) = cm / 30.48
    private fun Double.format(digits: Int) =
        String.format(Locale.US, "%.${digits}f", this)
}
