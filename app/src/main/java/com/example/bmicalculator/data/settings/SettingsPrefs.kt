package com.example.bmicalculator.data.settings

import android.content.Context

class SettingsPrefs(context: Context) {

    private val prefs =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setUnit(unit: UnitType) {
        prefs.edit().putString("unit", unit.name).apply()
    }

    fun getUnit(): UnitType {
        val value = prefs.getString("unit", UnitType.METRIC.name)
        return UnitType.valueOf(value!!)
    }
}
