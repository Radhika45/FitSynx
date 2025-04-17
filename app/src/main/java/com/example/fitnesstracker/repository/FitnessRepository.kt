package com.example.fitnesstracker.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.fitnesstracker.model.DailyFitnessModel
import com.example.fitnesstracker.model.WeeklyFitnessModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface FitnessRepository {
    fun getDailyFitnessData(context: Context): MutableLiveData<DailyFitnessModel>
    fun getWeeklyFitnessData(context: Context): MutableLiveData<WeeklyFitnessModel>
    fun getGoogleAccount(context: Context): GoogleSignInAccount
}