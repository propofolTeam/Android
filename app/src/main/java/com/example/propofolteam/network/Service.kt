package com.example.propofolteam.network

import com.example.propofolteam.data.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @POST("/user/join")
    fun requestSignUp(
        @Body signUpBody: RequestBody
    ) : Call<JSONObject>

    @POST("/user/login")
    fun sendLoginInformation(
        @Body LoginInformation : EmailLoginRequest
    ): Call<EmailLoginResponse>

    @POST("/post/write")
    fun post(
        @Header("Authorization") token: String,
        @Body postBody: RequestBody
    ):Call<JSONObject>

    @POST("/comment/{postId}")
    fun sendComment(
        @Header("Authorization") type:String,
        @Body CommentBody: CommentRequest,
        @Path("postId") id: Int
    ): Call<JSONObject>

    @GET("/post/{page}")
    fun getPost(
        @Header("Authorization") type:String,
        @Path("page") id: Int
    ): Call<PostResponse>

    @GET("/user/profile")
    fun getUserProfile(
        @Query("page") id: String,
        @Header("Authorization") type:String

    ): Call<UserProfileResponse>

    @GET("/post/{postId}/download/{fileName}")
    fun downloadPdf(
        @Header("Authorization") type:String,
        @Path("postId") postId: Long,
        @Path("fileName") fileName: String
    ): Call<PdfResponse>

}