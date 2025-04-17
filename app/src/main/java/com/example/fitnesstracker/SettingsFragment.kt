package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnesstracker.ui.EditProfileFragment

class SettingsFragment : Fragment() {

    private lateinit var settingsOptionsLayout: LinearLayout
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        settingsOptionsLayout = view.findViewById(R.id.settingsOptionsLayout)

        settingsViewModel.settingsOptions.observe(viewLifecycleOwner) { settingsOptions ->
            settingsOptionsLayout.removeAllViews() // Clear previous items before re-adding

            settingsOptions.forEach { setting ->
                val textView = TextView(requireContext()).apply {
                    text = setting
                    textSize = 20f
                    setPadding(16, 16, 16, 16)
                    setOnClickListener {
                        when (setting) {
                            "Contact Us" -> {
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout, ContactUsFragment()) // Make sure this ID is correct
                                    .addToBackStack(null)
                                    .commit()
                            }
                            "Logout" -> {
                                // Perform logout
                                authViewModel.logoutUser()
                                Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(requireContext(), LoginActivity::class.java))
                                requireActivity().finish()
                            }
                            "Edit Profile" -> {
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout, EditProfileFragment()) // Make sure this ID is correct
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }
                settingsOptionsLayout.addView(textView)
            }
        }

        return view
    }
}
