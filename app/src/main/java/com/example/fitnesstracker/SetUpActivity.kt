package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitnesstracker.databinding.ActivitySetUpBinding

class SetUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ✅ Retrieve the passed data
        val name = intent.getStringExtra("NAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")

        binding = ActivitySetUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system insets (status bar, nav bar)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle "Next" button click
        binding.buttonNext.setOnClickListener {
            // Replace with actual next activity or fragment
            val intent = Intent(this, GenderActivity::class.java)
            intent.putExtra("NAME", name)
            intent.putExtra("EMAIL", email)
            intent.putExtra("PASSWORD", password)
            startActivity(intent)
            finish()
        }
    }
}
