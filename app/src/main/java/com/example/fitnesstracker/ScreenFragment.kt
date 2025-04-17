package com.example.fitnesstracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class ScreenFragment : Fragment() {

    private var title: String? = null
    private var description: String? = null
    private var imageResId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESC)
            imageResId = it.getInt(ARG_IMAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen, container, false)

        val titleView: TextView = view.findViewById(R.id.title)
        val descView: TextView = view.findViewById(R.id.description)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        titleView.text = title
        descView.text = description
        imageView.setImageResource(imageResId)

        return view
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "description"
        private const val ARG_IMAGE = "image"

        fun newInstance(title: String, description: String, imageResId: Int) =
            ScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_DESC, description)
                    putInt(ARG_IMAGE, imageResId)
                }
            }
    }
}
