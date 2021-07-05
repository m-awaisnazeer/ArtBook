package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.communisolve.artbook.R
import com.communisolve.artbook.adapter.ImageRecyclerAdapter
import com.communisolve.artbook.databinding.FragmentImageApiBinding
import com.communisolve.artbook.util.Status
import com.communisolve.artbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel
    private var fragmentImageApiBinding: FragmentImageApiBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentImageApiBinding.bind(view)
        fragmentImageApiBinding = _binding
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        var job: Job? = null
        fragmentImageApiBinding?.searchText?.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        subscribeToObserver()
        _binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        _binding.imageRecyclerView.adapter = imageRecyclerAdapter
        imageRecyclerAdapter.setOnItemCLickListener {
            findNavController().popBackStack()
            viewModel.selectedImage(it)
        }
    }

    fun subscribeToObserver() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.stsus) {
                Status.SUCCESS -> {
                    val urls = it.data!!.hits.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentImageApiBinding?.progressBar!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    fragmentImageApiBinding?.progressBar!!.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }

                Status.LOADING -> {
                    fragmentImageApiBinding?.progressBar!!.visibility = View.VISIBLE

                }
            }
        })
    }
}