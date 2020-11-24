package com.example.propofolteam.workingRetrofit

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.data.EmailLoginRequest
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.EmailLoginResponse.Companion.instance
import com.example.propofolteam.data.UserProfileResponse
import com.example.propofolteam.module.LoginDialog
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.MainBottomActivity
import com.example.propofolteam.view.ui.profile.adapter.ProfileRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRetrofit {
    internal fun getProfile(
        getApplication: Application,
        context: Context
    ) {
        val profileService =
            (getApplication as com.example.propofolteam.application.PropofolApplication).retrofit.create(
                Service::class.java
            )

        val token = EmailLoginResponse.instance!!.accessToken
        profileService.getUserProfile("0","Bearer $token")
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                        //서버로부터 받은 정보들을 EmailLoginBody 변수에 담아준다
                        UserProfileResponse.instance = response.body()

                }
                //서버와 연결 실패
                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {

                }
            })
    }
}