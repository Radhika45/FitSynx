package com.example.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnboardingAdapter(
    private val items: List<OnboardingItem>,
    private val onNextClick: () -> Unit
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val titleText: TextView = view.findViewById(R.id.Title)
        private val icon: ImageView = view.findViewById(R.id.icon)
        private val idot: ImageView = view.findViewById(R.id.indicatorDots)
        private val nextButton: Button = view.findViewById(R.id.nextButton)

        fun bind(item: OnboardingItem, isLastPage: Boolean) {
            imageView.setImageResource(item.imageResId)
            titleText.text = item.title
            icon.setImageResource(item.iconResId)
            idot.setImageResource(item.indicatordotsResId)

            if (isLastPage) {
                nextButton.text = "Get Started"
            } else {
                nextButton.text = "Next"
            }

            nextButton.setOnClickListener {
                onNextClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position], position == items.size - 1)
    }

    override fun getItemCount(): Int = items.size
}

