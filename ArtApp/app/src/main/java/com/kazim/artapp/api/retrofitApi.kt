package com.kazim.artapp.api

import com.kazim.artapp.model.ArtProperty
import com.kazim.artapp.util.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitApi {


    @GET("/api/")
    suspend fun imageSearch(
        @Query("q")searchQuery:String,
        @Query("key")apikey:String=API_KEY
    ):Response<ArtProperty>
}