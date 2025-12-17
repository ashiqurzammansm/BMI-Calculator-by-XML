package com.example.bmicalculator.ui.main

import androidx.lifecycle.ViewModel
import com.example.bmicalculator.data.BmiRepository
import com.example.bmicalculator.model.BmiResult

class MainViewModel : ViewModel() {

    private val repository = BmiRepository()

    fun calculateBMI(
        weight: Int,
        height: Int,
        gender: String
    ): BmiResult {
        return repository.calculate(weight, height, gender)
    }
}
