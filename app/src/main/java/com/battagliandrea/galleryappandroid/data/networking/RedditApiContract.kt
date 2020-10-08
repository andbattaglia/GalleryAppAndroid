package com.battagliandrea.galleryappandroid.data.networking

import com.battagliandrea.data.networking.model.RDResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApiContract {

    @GET("r/{search}/top.json")
    suspend fun getImages(@Path("search") search: String): Response<RDResponse>
}