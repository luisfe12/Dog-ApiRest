package com.example.dogapirest13

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getDogsByBreeds(@Url url:String):Response<DogsResponse>
}