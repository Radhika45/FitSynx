package com.example.fitnesstracker

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fitnesstracker.model.WeeklyFitnessModel
import com.example.fitnesstracker.viewmodel.FitnessViewModel

class StatisticsViewModel : ViewModel() {

    private val fitnessViewModel = FitnessViewModel()

    fun getWeeklyFitnessData(context: Context): LiveData<WeeklyFitnessModel> {
        return fitnessViewModel.getWeeklyFitnessData(context)
    }
}
