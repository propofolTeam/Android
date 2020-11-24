package com.example.propofolteam.workingRetrofit

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.data.EmailLoginRequest
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.module.LoginDialog
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.MainBottomActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRetrofit {
    internal fun getEmailLogin(
        email: String,
        password: String,
        getApplication: Application,
        context: Context

    ) {
        //dialog 변수
        val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#0DE930")
        sweetAlertDialog
            .setTitleText("로딩 중")
            .setCancelable(false)
        sweetAlertDialog.show()

        val loginService =
            (getApplication as com.example.propofolteam.application.PropofolApplication).retrofit.create(
                Service::class.java
            )
        loginService.sendLoginInformation(EmailLoginRequest(email, password))
            .enqueue(object : Callback<EmailLoginResponse> {
                val loginDialog = LoginDialog()
                override fun onResponse(
                    call: Call<EmailLoginResponse>,
                    response: Response<EmailLoginResponse>
                ) {
                    //다음 화면으로 이동
                    val intent = Intent(context, MainBottomActivity::class.java)

                    //통신성공
                    if (response.code() == 200) {
                        //서버로부터 받은 정보들을 EmailLoginBody 변수에 담아준다
                        EmailLoginResponse.instance = response.body()
                        loginDialog.connectionSuccess(
                            response.code(),
                            context,
                            response.errorBody()?.string().toString(),
                            intent,
                            sweetAlertDialog
                        )
                        Log.d("token","token : ${EmailLoginResponse.instance!!.accessToken}")
                    }
                    //통신 실패
                    else if (response.code() == 401) {
                        loginDialog.connectionSuccess(
                            response.code(),
                            context,
                            response.errorBody()?.string().toString(),
                            intent,
                            sweetAlertDialog
                        )
                    }
                }
                //서버와 연결 실패
                override fun onFailure(call: Call<EmailLoginResponse>, t: Throwable) {
                    //LoginDialog 를 호출하여 서버와의 연결 실패를 dialog 로 띄운다
                    loginDialog.connectionFail(context, sweetAlertDialog)
                }
            })
    }
}