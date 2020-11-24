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

    @GET("/user/profile?userId=1&page=1")
    fun MyProfileInformationCheck(
        @Header("Authorization") type: String,
        @Body MyInformation: ProfileRequest
    ): Call<ProfileResponse>


}