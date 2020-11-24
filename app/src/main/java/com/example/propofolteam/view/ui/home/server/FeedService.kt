package com.example.propofolteam.view.ui.home.server

import com.example.propofolteam.data.FeedItemData
import retrofit2.Call
import retrofit2.http.*

interface FeedService {

    //피드 부분
    @GET("/post")
    fun requestFeed(
        @Header("Authorization") token: String,
        @Query ("page") page: Int
    ): Call<FeedItemData>

}