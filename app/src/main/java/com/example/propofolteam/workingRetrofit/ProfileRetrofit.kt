package com.example.propofolteam.workingRetrofit

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.ProfileRequest
import com.example.propofolteam.data.ProfileResponse
import com.example.propofolteam.network.Service
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ProfileRetrofit
internal fun getEmailLogin(
    Authorization: String,
    userId: String,
    page: Int,
    getApplication: Application,
    context: Context

) {
    val loginService =
        (getApplication as com.example.propofolteam.application.Application).retrofit.create(
            Service::class.java
        )
    loginService.MyProfileInformationCheck(Authorization,ProfileRequest(userId,page))
        .enqueue(object : retrofit2.Callback<ProfileResponse> {

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.code() == 200) {
                    //서버로부터 받은 정보들을 EmailLoginBody 변수에 담아준다
                    ProfileResponse.instance = response.body()
                    Log.d("imagename","imageName : ${ProfileResponse.instance!!.image}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.d("Fail","Fail : ${ProfileResponse.instance!!.image}")
            }

        })

}