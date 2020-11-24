package com.junhyuk.daedo.main.bottomItem.home.server

import com.example.propofolteam.data.FeedData
import retrofit2.Call
import retrofit2.http.*

interface FeedService {

    //피드 부분
    @GET("/post")
    fun requestFeed(
        @Query ("page") page: Int
    ): Call<FeedData>

}