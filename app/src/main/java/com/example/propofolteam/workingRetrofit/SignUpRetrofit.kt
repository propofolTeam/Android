package com.example.propofolteam.workingRetrofit

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.IntroFragment
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
            (getApplication as com.example.propofolteam.application.Application).retrofit.create(Service::class.java)

        //서버에 보낼 데이터
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

        builder.addFormDataPart("email", email)
        builder.addFormDataPart("password", password)
        builder.addFormDataPart("username", userName)

        if (profile != null){
            builder.addFormDataPart("profile", imageName, profile)
        }

        val signUpBody: RequestBody = builder.build()

        //signUp 서비스 결과 값
        signUpService.requestSignUp(signUpBody)
            .enqueue(object : Callback<SignUpResponse> {
                val signUpDialog = SignUpDialog()

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    signUpDialog.connectionFail(context, sweetAlertDialog)
                }

                override fun onResponse(
                    call: Call<SignUpResponse>,
                    response: Response<SignUpResponse>
                ) {
                    val intent = Intent(context, IntroFragment::class.java)

                    Log.d("500씨발련아", "data: ${response.errorBody()?.string()}")

                    signUpDialog.connectionSuccess(
                        response.code(),
                        context,
                        response.errorBody()?.string().toString(),
                        intent,
                        sweetAlertDialog
                    )

                }

            })
    }
}