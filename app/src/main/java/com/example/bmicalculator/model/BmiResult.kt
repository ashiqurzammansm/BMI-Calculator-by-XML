package com.example.bmicalculator.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BmiResult(
    val bmi: Double,
    val category: String,
    val description: String,
    val idealWeightRange: String,
    val gender: String
) : Parcelable
