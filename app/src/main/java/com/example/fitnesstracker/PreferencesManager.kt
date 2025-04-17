package com.example.fitnesstracker.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)

    fun saveStepData(steps: Int, calories: Double, distance: Double) {
        prefs.edit().apply {
            putInt("steps", steps)
            putFloat("calories", calories.toFloat())
            putFloat("distance", distance.toFloat())
            apply()
        }
    }

    fun getSteps(): Int = prefs.getInt("steps", 0)

    fun getCalories(): Double = prefs.getFloat("calories", 0f).toDouble()

    fun getDistance(): Double = prefs.getFloat("distance", 0f).toDouble()
}
