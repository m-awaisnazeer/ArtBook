package com.communisolve.artbook.repo

import androidx.lifecycle.LiveData
import com.communisolve.artbook.model.ImageResponse
import com.communisolve.artbook.roomdb.Art
import com.communisolve.artbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt(): LiveData<List<Art>>

    suspend fun searchImage(imageString: String): Resource<ImageResponse>
}