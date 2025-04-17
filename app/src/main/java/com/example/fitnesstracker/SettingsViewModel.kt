package com.example.fitnesstracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    // MutableLiveData to hold the list of settings options
    private val _settingsOptions = MutableLiveData<List<String>>()
    val settingsOptions: LiveData<List<String>> get() = _settingsOptions





    init {
        // Initialize the settings options with hardcoded data
        _settingsOptions.value = listOf("Contact Us", "Logout", "Edit Profile")
    }
}
