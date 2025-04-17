package com.example.fitnesstracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.LinearLayout

class GenderActivity : AppCompatActivity() {
    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gender)

        val btnMale = findViewById<LinearLayout>(R.id.btnMale)
        val btnFemale = findViewById<LinearLayout>(R.id.btnFemale)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")

        btnMale.setOnClickListener {
            selectedGender = "Male"
            btnMale.setBackgroundResource(R.drawable.background_blue)
        }

        btnFemale.setOnClickListener {
            selectedGender = "Female"
            btnFemale.setBackgroundResource(R.drawable.background_blue)
        }

        btnContinue.setOnClickListener {
            if (selectedGender != null) {
                // Proceed to the next screen (e.g., AgeActivity)
                val intent = Intent(this, AgeActivity::class.java)
                intent.putExtra("NAME", name)
                intent.putExtra("EMAIL", email)
                intent.putExtra("PASSWORD", password)
                intent.putExtra("GENDER", selectedGender)
                startActivity(intent)
            } else {
                btnContinue.error = "Please select a gender"
            }
        }

        // Back button click event
        btnBack.setOnClickListener {
            finish() // Go back to the previous screen
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

