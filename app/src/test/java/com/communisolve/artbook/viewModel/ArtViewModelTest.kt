package com.communisolve.artbook.viewModel

import com.communisolve.artbook.repo.FakeArtRepository
import com.communisolve.artbook.viewmodel.ArtViewModel
import org.junit.Before

class ArtViewModelTest {
    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        //Testing Doubles means Fakes, not original bit have same functionality of original
         viewModel = ArtViewModel(FakeArtRepository())

    }
}