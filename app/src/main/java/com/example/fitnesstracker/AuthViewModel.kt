package com.example.fitnesstracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance() // ✅ Initialize FirebaseAuth
    private val _authResult = MutableLiveData<Pair<Boolean, String?>>()
    val authResult: LiveData<Pair<Boolean, String?>> = _authResult // ✅ Expose LiveData

    fun registerUser(name: String, gender: String, age: Int, email: String, password: String,  callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // ✅ Save user data to Firebase Realtime Database
                    val user = mapOf(
                        "userId" to userId,
                        "name" to name,
                        "email" to email,
                        "gender" to gender,
                        "age" to age
                    )

                    FirebaseDatabase.getInstance().getReference("Users").child(userId)
                        .setValue(user)
                        .addOnSuccessListener {
                            callback(true, null) // ✅ Success
                        }
                        .addOnFailureListener { e ->
                            callback(false, e.message) // ❌ Failed to save data
                        }
                } else {
                    callback(false, task.exception?.message) // ❌ Registration failed
                }
            }
    }

    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null) // ✅ Login successful
                } else {
                    callback(false, task.exception?.message) // ❌ Login failed
                }
            }
    }

    fun updateUser(name: String, gender: String, age: Int,email: String,  callback: (Boolean, String?) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userUpdates = mapOf(
                "name" to name,
                "gender" to gender,
                "age" to age,
                "email" to email
            )

            FirebaseDatabase.getInstance().getReference("Users").child(userId)
                .updateChildren(userUpdates)
                .addOnSuccessListener {
                    callback(true, null)
                }
                .addOnFailureListener { e ->
                    callback(false, e.message)
                }
        } else {
            callback(false, "User not logged in.")
        }
    }

    fun logoutUser() {
        auth.signOut() // ✅ Logs the user out
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null // ✅ Check if a user is logged in
    }
}