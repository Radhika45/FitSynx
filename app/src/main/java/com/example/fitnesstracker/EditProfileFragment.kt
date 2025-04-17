package com.example.fitnesstracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnesstracker.AuthViewModel
import com.example.fitnesstracker.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = auth.currentUser?.uid ?: return

        // Load existing user data
        database.child("Users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value?.toString() ?: ""
                val gender = snapshot.child("gender").value?.toString() ?: ""
                val age = snapshot.child("age").value?.toString() ?: ""
                val email = snapshot.child("email").value?.toString() ?: ""

                binding.textViewName.text = "Name: $name"
                binding.textViewGender.text = "Gender: $gender"
                binding.textViewAge.text = "Age: $age"
                binding.textViewEmail.text = "Email: $email"

                binding.editName.setText(name)
                binding.editGender.setText(gender)
                binding.editAge.setText(age)
                binding.editEmail.setText(email)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
            }
        })

        binding.buttonedit.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val gender = binding.editGender.text.toString().trim()
            val age = binding.editAge.text.toString().trim().toIntOrNull() ?: -1
            val email = binding.editEmail.text.toString().trim()

            if (name.isEmpty() || gender.isEmpty() || age <= 0 || email.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.updateUser(name, gender, age, email) { success, message ->
                if (success) {
                    Toast.makeText(requireContext(), "Profile updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
