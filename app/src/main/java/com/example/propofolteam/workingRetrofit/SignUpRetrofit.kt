package com.example.propofolteam.workingRetrofit

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.R
import com.example.propofolteam.module.SignUpDialog
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.IntroFragment
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRetrofit {
    internal fun setupRetrofit(
        email: String,
        password: String,
        userName: String,
        profile: RequestBody?,
        imageName: String,
        getApplication: Application,
        context: Context
    ) {

        //로딩 다이얼로그
        val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#0DE930")
        sweetAlertDialog
            .setTitleText("로딩 중")
            .setCancelable(false)
        sweetAlertDialog.show()

        //signUp 서비스
        val signUpService =
            (getApplication as com.example.propofolteam.application.Application).retrofit.create(
                Service::class.java
            )

        //서버에 보낼 데이터
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        builder.addFormDataPart("email", email)
        builder.addFormDataPart("password", password)
        builder.addFormDataPart("name", userName)

        if (profile != null) {
            builder.addFormDataPart("image", imageName, profile)
        } else {
            builder.addFormDataPart("image",R.drawable.ic_profile.toString())
        }

        val signUpBody: RequestBody = builder.build()

        signUpService.requestSignUp(signUpBody)
            .enqueue(object : Callback<JSONObject> {

                val signUpDialog = SignUpDialog()

                override fun onResponse(
                    call: Call<JSONObject>,
                    response: Response<JSONObject>
                ) {
                    Log.d("test", "test")
                    signUpDialog.connectionSuccess(
                        response.code(),
                        context,
                        response.errorBody()?.string().toString(),
                        sweetAlertDialog
                    )
                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    signUpDialog.connectionFail(context, sweetAlertDialog)
                }

            })
    }
}