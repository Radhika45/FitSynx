package com.example.fitnesstracker

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class AuthRepository(context: Context) {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // ✅ User Registration
    fun registerUser(name: String, gender: String, age: Int, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null) // Success
                } else {
                    callback(false, task.exception?.message) // Error message
                }
            }
    }

    // ✅ User Login
    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null) // Success
                } else {
                    callback(false, task.exception?.message) // Error message
                }
            }
    }

    // ✅ User Logout
    fun logoutUser() {
        firebaseAuth.signOut()
    }

    // ✅ Check if User is Logged In
    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}