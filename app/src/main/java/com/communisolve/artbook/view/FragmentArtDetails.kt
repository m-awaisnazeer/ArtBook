package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.communisolve.artbook.R
import com.communisolve.artbook.databinding.FragmentArtDetailBinding
import com.communisolve.artbook.util.Status
import com.communisolve.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class FragmentArtDetails @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_detail) {

    private lateinit var viewModel: ArtViewModel

    var fragmentArtDetailBinding: FragmentArtDetailBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentArtDetailBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        fragmentArtDetailBinding = _binding

        subscribeToObserver()

        _binding.imageView2.setOnClickListener {
            findNavController().navigate(FragmentArtDetailsDirections.actionFragmentArtDetailsToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)


        _binding.button2.setOnClickListener {
            viewModel.makeArt(
                _binding.edtName.text.toString(),
                _binding.edtArtist.text.toString(),
                _binding.edtYear.text.toString()
            )
        }

    }

    private fun subscribeToObserver() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, { url ->
            fragmentArtDetailBinding?.let { binding ->
                glide.load(url).into(binding.imageView2)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.stsus) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentArtDetailBinding = null
        super.onDestroyView()
    }
}