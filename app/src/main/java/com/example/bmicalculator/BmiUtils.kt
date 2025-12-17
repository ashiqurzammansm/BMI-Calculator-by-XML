package com.example.bmicalculator

object BmiUtils {

    fun calculateBMI(weight: Int, heightCm: Int): Double {
        val heightMeter = heightCm / 100.0
        return weight / (heightMeter * heightMeter)
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
            "NORMAL" -> 0xFF00E676.toInt()     // Green
            "UNDERWEIGHT" -> 0xFFFFD600.toInt() // Yellow
            "OVERWEIGHT" -> 0xFFFF9100.toInt()  // Orange
            else -> 0xFFFF1744.toInt()          // Red
        }
    }

    fun getDescription(category: String): String {
        return when (category) {
            "NORMAL" -> "You have a normal body weight. Good job!"
            "UNDERWEIGHT" -> "You are underweight. Try to eat nutritious food."
            "OVERWEIGHT" -> "You are slightly overweight. Exercise is recommended."
            else -> "You are obese. Please consult a doctor."
        }
    }
}
