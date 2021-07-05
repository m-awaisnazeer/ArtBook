package com.communisolve.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.communisolve.artbook.adapter.ArtRecyclerAdapter
import com.communisolve.artbook.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ImageAPIFragment::class.java.name -> ImageAPIFragment(imageRecyclerAdapter)
            FragmentArtDetails::class.java.name -> FragmentArtDetails(glide)
            else -> return super.instantiate(classLoader, className)
        }

    }
}