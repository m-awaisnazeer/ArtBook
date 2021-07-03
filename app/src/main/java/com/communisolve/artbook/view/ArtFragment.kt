package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.communisolve.artbook.R
import com.communisolve.artbook.databinding.FragmentsArtsBinding

class ArtFragment : Fragment(R.layout.fragments_arts) {

    private var fragmentBinding: FragmentsArtsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var binding = FragmentsArtsBinding.bind(view)
        fragmentBinding = binding

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToFragmentArtDetails())
        }


    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}