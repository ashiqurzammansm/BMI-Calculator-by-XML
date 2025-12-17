package com.example.bmicalculator.data

import com.example.bmicalculator.model.BmiResult
import com.example.bmicalculator.util.BmiUtils

class BmiRepository {

    fun calculate(
        weight: Int,
        height: Int,
        gender: String
    ): BmiResult {

        val bmi = BmiUtils.calculateBMI(weight, height)
        val category = BmiUtils.getCategory(bmi)
        val description = BmiUtils.getDescription(category, gender)
        val idealRange = BmiUtils.idealWeightRange(height)

        return BmiResult(
            bmi = bmi,
            category = category,
            description = description,
            idealWeightRange = idealRange,
            gender = gender
        )
    }
}
