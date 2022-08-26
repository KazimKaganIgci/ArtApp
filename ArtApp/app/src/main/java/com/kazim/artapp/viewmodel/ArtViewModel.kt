package com.kazim.artapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazim.artapp.Repo.ArtRepositoryInterface
import com.kazim.artapp.model.ArtProperty
import com.kazim.artapp.room.Art
import com.kazim.artapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

//güncelleme
@HiltViewModel
class ArtViewModel @Inject constructor(
    private val repository: ArtRepositoryInterface
):ViewModel(){

    //Art Fragment
    val artList =repository.getArt()

    //Image API Fragment
    private val images = MutableLiveData<Resource<ArtProperty>>()
    val imageList :LiveData<Resource<ArtProperty>>
        get() = images

    private val selectImage =MutableLiveData<String>()
    val selectImageUrl :LiveData<String>
        get() = selectImage

    //ArtDetails Fragment
    private var insertArtMsg =MutableLiveData<Resource<Art>>()
    val insertArtMessage:LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInstertArtMsg(){
        insertArtMsg=MutableLiveData<Resource<Art>>()
    }
    fun setSelectedImage(url:String){
        selectImage.postValue(url)
    }
    fun deleteArt(art:Art)=viewModelScope.launch {
        repository.deleteArt(art)
    }
    fun insertArt(art: Art)=viewModelScope.launch {
        repository.insertArt(art)
    }
    fun makeArt(name:String,artistName:String,year:String){
        if (name.isEmpty() ||artistName.isEmpty()||year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter name,artist,year",null))
            return

        }
        val yearInt=try {
            year.toInt()

        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }
        val art =Art(name,artistName,yearInt,selectImage.value ?:"")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage(searchString:String){
        if (searchString.isEmpty()){
            return
        }
        images.value =Resource.loading(null)
        viewModelScope.launch {
            val  response =repository.searchImage(searchString)
            images.value =response
        }

    }




}