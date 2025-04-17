package com.example.fitnesstracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.example.fitnesstracker.databinding.FragmentStatisticsBinding
import com.example.fitnesstracker.viewmodel.HomeViewModel

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var progressChart: BarChart
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        progressChart = binding.progressChart

        homeViewModel.init(requireContext()) // Only once

        observeLiveData()

        return binding.root
    }

    private fun observeLiveData() {
        homeViewModel.stepCount.observe(viewLifecycleOwner) { steps ->
            updateChart(steps, homeViewModel.caloriesBurned.value ?: 0.0, homeViewModel.distanceTraveled.value ?: 0.0)
        }

        homeViewModel.caloriesBurned.observe(viewLifecycleOwner) { calories ->
            updateChart(homeViewModel.stepCount.value ?: 0, calories, homeViewModel.distanceTraveled.value ?: 0.0)
        }

        homeViewModel.distanceTraveled.observe(viewLifecycleOwner) { distance ->
            updateChart(homeViewModel.stepCount.value ?: 0, homeViewModel.caloriesBurned.value ?: 0.0, distance)
        }
    }

    private fun updateChart(steps: Int, calories: Double, distance: Double) {
        val entries = listOf(
            BarEntry(0f, steps.toFloat()),
            BarEntry(1f, calories.toFloat()),
            BarEntry(2f, distance.toFloat())
        )

        val dataSet = BarDataSet(entries, "Daily Stats").apply {
            setColors(
                ContextCompat.getColor(requireContext(), R.color.steps),
                ContextCompat.getColor(requireContext(), R.color.calories),
                ContextCompat.getColor(requireContext(), R.color.distance)
            )
            valueTextSize = 12f
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.4f
        }

        progressChart.apply {
            data = barData
            description.isEnabled = false
            setTouchEnabled(false)
            setDrawGridBackground(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                labelCount = 3
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(listOf("Steps", "Calories", "Distance"))
            }

            axisLeft.apply {
                axisMinimum = 0f
                setDrawGridLines(false)
            }

            axisRight.isEnabled = false
            legend.isEnabled = true

            notifyDataSetChanged()
            invalidate()
        }
    }
}
