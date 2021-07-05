package com.communisolve.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.communisolve.artbook.R
import com.communisolve.artbook.adapter.ArtRecyclerAdapter
import com.communisolve.artbook.databinding.FragmentsArtsBinding
import com.communisolve.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragments_arts) {
    lateinit var viewModel: ArtViewModel
    private var fragmentBinding: FragmentsArtsBinding? = null


    private val swipeCallBack = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val  selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        var binding = FragmentsArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObserver()

        binding.recyclerViewArt.adapter = artRecyclerAdapter
        binding.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerViewArt)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToFragmentArtDetails())
        }
    }


    private fun subscribeToObserver(){
        viewModel.artList.observe(viewLifecycleOwner, {
            artRecyclerAdapter.arts = it
        })
    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}