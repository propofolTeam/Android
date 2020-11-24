package com.example.propofolteam.network

import com.example.propofolteam.data.EmailLoginRequest
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.UserProfileResponse
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

    @GET("/user/profile?")
    fun getProfile(
        @Query("page") page : String,
        @Header("Authorization") token: String
    ):Call<UserProfileResponse>
}