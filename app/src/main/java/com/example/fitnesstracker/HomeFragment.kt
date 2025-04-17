package com.example.fitnesstracker

import android.Manifest
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fitnesstracker.utils.PreferencesManager
import com.example.fitnesstracker.viewmodel.HomeViewModel

class HomeFragment : Fragment(), SensorEventListener {

    private lateinit var textViewSteps: TextView
    private lateinit var textViewStepsBig: TextView
    private lateinit var textViewCalories: TextView
    private lateinit var textViewDistance: TextView
    private lateinit var stepsProgressBar: ProgressBar
    private lateinit var textViewUserName: TextView  // Add this for the user's name

    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()  // Add this to access ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize sensor manager and step counter
        sensorManager = requireContext().getSystemService(SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // Initialize ViewModel preferences
        homeViewModel.init(requireContext())

        // Check if the device has the required sensor
        if (stepSensor == null) {
            Toast.makeText(requireContext(), "No Step Counter Sensor found!", Toast.LENGTH_SHORT).show()
        }

        // Load user data from ProfileViewModel
        profileViewModel.loadUserData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // UI elements
        textViewSteps = rootView.findViewById(R.id.show_steps)
        textViewStepsBig = rootView.findViewById(R.id.duplicate_steps)
        textViewCalories = rootView.findViewById(R.id.burned_calories)
        textViewDistance = rootView.findViewById(R.id.distance)
        stepsProgressBar = rootView.findViewById(R.id.stepsProgressBar)

        // Add TextView for displaying user's name
        textViewUserName = rootView.findViewById(R.id.textView3)  // Replace with your actual TextView ID

        stepsProgressBar.max = 10000 // Goal steps

        // Observe LiveData from ProfileViewModel for the user's name
        profileViewModel.userProfile.observe(viewLifecycleOwner) { userProfile ->
            textViewUserName.text = "Welcome\n \t${userProfile.name}"  // Update TextView with the user's name
        }

        // Observe LiveData from ViewModel
        homeViewModel.stepCount.observe(viewLifecycleOwner) {
            textViewSteps.text = it.toString()
            textViewStepsBig.text = it.toString()
        }

        homeViewModel.caloriesBurned.observe(viewLifecycleOwner) {
            textViewCalories.text = String.format("%.2f", it)
        }

        homeViewModel.distanceTraveled.observe(viewLifecycleOwner) {
            textViewDistance.text = String.format("%.2f", it)
        }

        homeViewModel.progress.observe(viewLifecycleOwner) {
            stepsProgressBar.progress = it
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()

        // Request permission for step tracking (for Android 10+)
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                1001
            )
        } else {
            // Register the listener if permission granted
            stepSensor?.let {
                sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val currentSteps = event.values[0].toInt()
            homeViewModel.updateStepData(currentSteps)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No-op
    }
}
