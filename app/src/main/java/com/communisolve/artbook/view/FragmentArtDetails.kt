package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.communisolve.artbook.R
import com.communisolve.artbook.databinding.FragmentArtDetailBinding

class FragmentArtDetails : Fragment(R.layout.fragment_art_detail) {

    var fragmentArtDetailBinding: FragmentArtDetailBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentArtDetailBinding.bind(view)
        fragmentArtDetailBinding = _binding

        _binding.imageView2.setOnClickListener {
            findNavController().navigate(FragmentArtDetailsDirections.actionFragmentArtDetailsToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }


    override fun onDestroyView() {
        fragmentArtDetailBinding = null
        super.onDestroyView()
    }
}