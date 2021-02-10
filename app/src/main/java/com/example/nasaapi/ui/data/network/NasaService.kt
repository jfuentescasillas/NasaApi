package com.example.nasaapi.ui.data.network

import com.example.nasaapi.ui.data.model.NasaResponseDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://images-api.nasa.gov//search?q=sun

interface NasaService {
    @GET("search")
    suspend fun getNasaImages(@Query("q") pictureType: String): NasaResponseDataModel
}