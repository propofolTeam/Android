package com.junhyuk.daedo.main.bottomItem.home.server

import com.junhyuk.daedo.main.bottomItem.home.data.FeedData
import com.junhyuk.daedo.main.bottomItem.home.data.LikeResponse
import retrofit2.Call
import retrofit2.http.*

interface FeedService {

    //피드 부분
    @GET("/feed/post/all")
    fun requestFeed(
        @Query ("page") page: Int
    ): Call<List<FeedData>>

}