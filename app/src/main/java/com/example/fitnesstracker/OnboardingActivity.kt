package com.example.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)

        val onboardingItems = listOf(
            OnboardingItem(R.drawable.bg2, "Start Your Journey Towards A More Active Lifestyle", R.drawable.steps, R.drawable.idots1),
            OnboardingItem(R.drawable.bg1, "Find Nutrition Tips That Fit Your Lifestyle", R.drawable.calories, R.drawable.idots2),
            OnboardingItem(R.drawable.img1, "A Community For You, Challenge Yourself!", R.drawable.steps, R.drawable.idots3)
        )

        adapter = OnboardingAdapter(onboardingItems) {
            if (viewPager.currentItem + 1 < onboardingItems.size) {
                viewPager.currentItem += 1
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        viewPager.adapter = adapter
    }
}
