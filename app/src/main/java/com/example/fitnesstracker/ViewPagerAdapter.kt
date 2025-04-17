package com.example.fitnesstracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val screens: List<ScreenFragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = screens.size

    override fun createFragment(position: Int): Fragment = screens[position]
}
