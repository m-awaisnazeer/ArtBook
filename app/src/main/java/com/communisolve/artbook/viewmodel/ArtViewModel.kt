package com.communisolve.artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.communisolve.artbook.model.ImageResponse
import com.communisolve.artbook.repo.ArtRepository
import com.communisolve.artbook.repo.ArtRepositoryInterface
import com.communisolve.artbook.roomdb.Art
import com.communisolve.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
)  : ViewModel() {


    //Art Fragment

    val artList = repository.getArt()

    //Image API Fragment
    private val _images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = _images

    private val _selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = _selectedImage

    //Art Details Fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage: LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun selectedImage(url: String) {
        _selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repository.deleteArt(art)
    }


    fun insertArt(art: Art) = viewModelScope.launch {
        repository.insertArt(art)
    }

    fun makeArt(name: String, artistname: String, year: String) {
        if (name.isEmpty() || artistname.isEmpty() || year.isEmpty()) {
            insertArtMsg.postValue(Resource.error("Enter name, artist",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("year should be number",null))
            return
        }

        val art = Art(name,artistname,yearInt,_selectedImage.value!!?:"")
        insertArt(art)
        selectedImage("")
        insertArtMsg.postValue(Resource.success(art))

    }

    fun searchForImage(searchString: String) {
        if (searchString.isEmpty()) {
            return
        }

        _images.value = Resource.loading(null)

        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            _images.postValue(response)

            //  _images.value = (response)
        }
    }


}