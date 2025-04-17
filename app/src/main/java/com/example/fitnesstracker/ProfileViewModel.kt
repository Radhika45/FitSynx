package com.example.fitnesstracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

data class UserProfile(val name: String, val gender: String, val age: String, val email: String)

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference.child("Users")

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> get() = _userProfile

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val name = snapshot.child("name").value.toString()
                        val gender = snapshot.child("gender").value.toString()
                        val age = snapshot.child("age").value.toString()
                        val email = snapshot.child("email").value.toString()

                        _userProfile.value = UserProfile(name, gender, age, email)
                    } else {
                        _errorMessage.value = "User data not found!"
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _errorMessage.value = "Failed to load data"
                }
            })
        }
    }

    fun logout() {
        auth.signOut()
    }
}
