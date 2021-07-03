package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.communisolve.artbook.R
import com.communisolve.artbook.databinding.FragmentImageApiBinding

class ImageAPIFragment : Fragment(R.layout.fragment_image_api) {
    private var fragmentImageApiBinding: FragmentImageApiBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentImageApiBinding.bind(view)
        fragmentImageApiBinding = _binding
    }
}