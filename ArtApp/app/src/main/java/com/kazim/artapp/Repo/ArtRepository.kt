package com.kazim.artapp.Repo

import androidx.lifecycle.LiveData
import com.kazim.artapp.api.retrofitApi
import com.kazim.artapp.model.ArtProperty
import com.kazim.artapp.room.Art
import com.kazim.artapp.room.ArtDao
import com.kazim.artapp.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: retrofitApi) :ArtRepositoryInterface{
    override suspend fun insertArt(art: Art) {
       artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ArtProperty> {
        return try {
            val response =retrofitApi.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?:Resource.error("error",null)


            }else{
                Resource.error("error",null)
            }


        }catch (e:Exception){
           Resource.error("No data!",null)
        }
    }

}