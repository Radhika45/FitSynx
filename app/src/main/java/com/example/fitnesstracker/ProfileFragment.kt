package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnesstracker.databinding.FragmentProfileBinding
import com.example.fitnesstracker.ui.EditProfileFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.loadUserData()

        viewModel.userProfile.observe(viewLifecycleOwner) { user ->
            binding.textViewName.text = "Name: ${user.name}"
            binding.textViewGender.text = "Gender: ${user.gender}"
            binding.textViewAge.text = "Age: ${user.age}"
            binding.textViewEmail.text = "Email: ${user.email}"
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.buttonEdit.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, EditProfileFragment()) // Make sure this ID is correct
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
