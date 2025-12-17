package com.example.bmicalculator.util

object BmiUtils {

    fun calculateBMI(weight: Int, heightCm: Int): Double {
        val heightM = heightCm / 100.0
        return weight / (heightM * heightM)
    }

    fun getCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "UNDERWEIGHT"
            bmi < 25 -> "NORMAL"
            bmi < 30 -> "OVERWEIGHT"
            else -> "OBESE"
        }
    }

    fun getCategoryColor(category: String): Int {
        return when (category) {
            "UNDERWEIGHT" -> 0xFFFFC107.toInt() // Yellow
            "NORMAL" -> 0xFF4CAF50.toInt()      // Green
            "OVERWEIGHT" -> 0xFFFF9800.toInt()  // Orange
            else -> 0xFFF44336.toInt()          // Red
        }
    }

    fun getDescription(category: String, gender: String): String {
        return when (category) {
            "UNDERWEIGHT" ->
                "You are underweight. Consider nutritious meals." +
                        if (gender == "FEMALE") " Focus on iron & calcium." else ""
            "NORMAL" ->
                "You have a healthy body weight. Keep it up!"
            "OVERWEIGHT" ->
                "You are slightly overweight. Regular exercise is recommended."
            else ->
                "You are obese. Please consult a healthcare professional."
        }
    }

    fun idealWeightRange(heightCm: Int): String {
        val h = heightCm / 100.0
        val min = 18.5 * h * h
        val max = 24.9 * h * h
        return "${min.toInt()}kg – ${max.toInt()}kg"
    }
}