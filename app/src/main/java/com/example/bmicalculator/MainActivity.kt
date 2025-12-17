package com.example.bmicalculator

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var height = 170
    private var weight = 60
    private var age = 25

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateMinusState()

        // Height slider
        binding.seekHeight.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                height = if (progress < 50) 50 else progress
                animateText(binding.tvHeight, height)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Weight
        binding.btnWeightPlus.setOnClickListener {
            weight++
            animateText(binding.tvWeight, weight)
            updateMinusState()
        }

        binding.btnWeightMinus.setOnClickListener {
            if (weight > 1) {
                weight--
                animateText(binding.tvWeight, weight)
                updateMinusState()
            }
        }

        // Age
        binding.btnAgePlus.setOnClickListener {
            age++
            animateText(binding.tvAge, age)
            updateMinusState()
        }

        binding.btnAgeMinus.setOnClickListener {
            if (age > 1) {
                age--
                animateText(binding.tvAge, age)
                updateMinusState()
            }
        }

        // Calculate
        binding.btnCalculate.setOnClickListener {
            val bmi = BmiUtils.calculateBMI(weight, height)
            val category = BmiUtils.getCategory(bmi)
            val desc = BmiUtils.getDescription(category)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("BMI", bmi)
            intent.putExtra("CATEGORY", category)
            intent.putExtra("DESC", desc)
            startActivity(intent)
        }
    }

    // Disable minus when minimum reached
    private fun updateMinusState() {
        binding.btnWeightMinus.isEnabled = weight > 1
        binding.btnAgeMinus.isEnabled = age > 1
    }

    // Animate number change
    private fun animateText(textView: android.widget.TextView, value: Int) {
        textView.text = value.toString()
        ObjectAnimator.ofFloat(textView, "scaleX", 0.8f, 1f).setDuration(150).start()
        ObjectAnimator.ofFloat(textView, "scaleY", 0.8f, 1f).setDuration(150).start()
    }
}
