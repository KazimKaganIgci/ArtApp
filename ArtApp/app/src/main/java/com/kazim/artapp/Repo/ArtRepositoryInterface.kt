package com.kazim.artapp.Repo

import androidx.lifecycle.LiveData
import com.kazim.artapp.model.ArtProperty
import com.kazim.artapp.room.Art
import com.kazim.artapp.util.Resource

interface ArtRepositoryInterface  {
    suspend fun insertArt(art:Art)
    suspend fun deleteArt(art:Art)
    fun getArt():LiveData<List<Art>>
    suspend fun searchImage(imageString: String) :Resource<ArtProperty>



}
