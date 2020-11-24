package com.example.propofolteam.network

import com.example.propofolteam.data.EmailLoginRequest
import com.example.propofolteam.data.EmailLoginResponse
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {
    @POST("/user/join")
    fun requestSignUp(
        @Body signUpBody: RequestBody
    ) : Call<JSONObject>

    @POST("/user/login")
    fun sendLoginInformation(
        @Body LoginInformation : EmailLoginRequest
    ): Call<EmailLoginResponse>
    
}