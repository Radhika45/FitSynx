package com.example.fitnesstracker.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.utils.PreferencesManager

class HomeViewModel : ViewModel() {

    private lateinit var prefs: PreferencesManager

    fun init(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = PreferencesManager(context)
            loadSavedData()
        }
    }

    private val _stepCount = MutableLiveData<Int>()
    val stepCount: LiveData<Int> get() = _stepCount

    private val _caloriesBurned = MutableLiveData<Double>()
    val caloriesBurned: LiveData<Double> get() = _caloriesBurned

    private val _distanceTraveled = MutableLiveData<Double>()
    val distanceTraveled: LiveData<Double> get() = _distanceTraveled

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private var initialStepCount: Int = -1
    private val caloriesBurnedPerStep: Double = 0.04
    private val distancePerStep: Double = 0.0008

    fun updateStepData(currentSteps: Int) {
        if (initialStepCount == -1) {
            initialStepCount = currentSteps
        }

        val stepsToday = currentSteps - initialStepCount
        val calories = stepsToday * caloriesBurnedPerStep
        val distance = stepsToday * distancePerStep

        _stepCount.value = stepsToday
        _caloriesBurned.value = calories
        _distanceTraveled.value = distance
        _progress.value = stepsToday

        prefs.saveStepData(stepsToday, calories, distance)
    }

    private fun loadSavedData() {
        val savedSteps = prefs.getSteps()
        val savedCalories = prefs.getCalories()
        val savedDistance = prefs.getDistance()

        _stepCount.value = savedSteps
        _caloriesBurned.value = savedCalories
        _distanceTraveled.value = savedDistance
        _progress.value = savedSteps
    }
}
