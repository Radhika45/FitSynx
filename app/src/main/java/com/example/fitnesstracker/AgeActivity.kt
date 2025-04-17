package com.example.fitnesstracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class AgeActivity : AppCompatActivity() {
    private lateinit var txtDOB: TextView
    private lateinit var btnContinue: Button
    private var selectedDOB: String? = null  // Store selected DOB

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            selectedDOB = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            txtDOB.text = selectedDOB
            btnContinue.isEnabled = true  // Enable continue button after selection
        }, year, month, day)
        datePicker.datePicker.maxDate = System.currentTimeMillis() // Restrict future dates
        datePicker.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_age)

        txtDOB = findViewById(R.id.txtDOB)
        btnContinue = findViewById(R.id.btnContinue)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")
        val gender = intent.getStringExtra("GENDER")

        // Open Date Picker when TextView is clicked
        txtDOB.setOnClickListener {
            showDatePicker()
        }

        // Continue button click event
        btnContinue.setOnClickListener {
            if (selectedDOB != null) {
                try {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val dobDate = LocalDate.parse(selectedDOB, formatter)
                    val currentDate = LocalDate.now()
                    val age = Period.between(dobDate, currentDate).years

                    val intent = Intent(this, ConfirmActivity::class.java)
                    intent.putExtra("DOB", selectedDOB)
                    intent.putExtra("NAME", name)
                    intent.putExtra("AGE", age)
                    intent.putExtra("EMAIL", email)
                    intent.putExtra("PASSWORD", password)
                    intent.putExtra("GENDER", gender)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Invalid date selected!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select your Date of Birth", Toast.LENGTH_SHORT).show()
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

