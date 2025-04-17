package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fitnesstracker.databinding.ActivityConfirmBinding

class ConfirmActivity : AppCompatActivity() {

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var gender: String
    private lateinit var DOB: String
    private var age: Int = 0
    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: ActivityConfirmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Get data from Intent
        name = intent.getStringExtra("NAME") ?: ""
        email = intent.getStringExtra("EMAIL") ?: ""
        password = intent.getStringExtra("PASSWORD") ?: ""
        gender = intent.getStringExtra("GENDER") ?: ""
        DOB = intent.getStringExtra("DOB") ?: ""
        age = intent.getIntExtra("AGE", 0)

        // Set data to TextViews
        binding.tvName.text = "Name: $name"
        binding.tvEmail.text = "Email: $email"
        binding.tvGender.text = "Gender: $gender"
        binding.tvAge.text = "Age: $age"
        binding.tvdob.text = "D.O.B: $DOB"

        binding.btnConfirm.setOnClickListener {
            authViewModel.registerUser(name, gender, age, email, password) { success, message ->
                if (success) {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, message ?: "Registration Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}