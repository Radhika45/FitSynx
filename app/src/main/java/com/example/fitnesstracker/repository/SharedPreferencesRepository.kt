package com.example.fitnesstracker.repository

import android.content.Context
import android.content.SharedPreferences

interface SharedPreferencesRepository {
    fun saveObjectiveSteps(context: Context, objectiveSteps: Int)
    fun loadObjectiveSteps(context: Context): Int
    fun getSharedPreferences(context: Context): SharedPreferences
}