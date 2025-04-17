package com.example.fitnesstracker

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonReset: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.editTextEmail)
        buttonReset = findViewById(R.id.buttonReset)
        val btnBack = findViewById<Button>(R.id.btnBack)

        buttonReset.setOnClickListener {
            val email = editTextEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                editTextEmail.error = "Enter your registered email"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        // Back button click event
        btnBack.setOnClickListener {
            finish() // Go back to the previous screen
        }
    }
}
