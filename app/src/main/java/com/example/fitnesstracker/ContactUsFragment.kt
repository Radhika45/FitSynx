package com.example.fitnesstracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
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

class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)

        // Bind views
        val btnSend: Button = view.findViewById(R.id.btnSend)
        val btnBack: Button = view.findViewById(R.id.btnBack)
        val etName: TextInputEditText = view.findViewById(R.id.etName)
        val etEmail: TextInputEditText = view.findViewById(R.id.etEmail)
        val etPhone: TextInputEditText = view.findViewById(R.id.etPhone)
        val etMessage: TextInputEditText = view.findViewById(R.id.etMessage)

        btnSend.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val message = etMessage.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || message.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Message Sent Successfully!", Toast.LENGTH_LONG).show()
            }
        }

        // Back button click event
        btnBack.setOnClickListener {
            requireActivity().onBackPressed() // Go back to the previous screen
        }

        return view
    }
}
