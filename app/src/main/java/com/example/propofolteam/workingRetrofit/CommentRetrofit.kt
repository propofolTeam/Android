package com.example.propofolteam.workingRetrofit


import android.app.Application
import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.application.PropofolApplication
import com.example.propofolteam.data.CommentRequest
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.module.CommentDialog
import com.example.propofolteam.network.Service
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentRetrofit {
    internal fun sendComment(
        Comment: String,
        getApplication: Application,
        context: Context,
        position: Int
    ) {
        //dialog 변수
        val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.progressHelper.barColor = Color.parseColor("#0DE930")
        sweetAlertDialog
            .setTitleText("로딩 중")
            .setCancelable(false)
        sweetAlertDialog.show()

        val commentService =
            (getApplication as PropofolApplication).retrofit.create(
                Service::class.java
            )
        var token = EmailLoginResponse.instance!!.accessToken
        commentService.sendComment("Bearer $token",CommentRequest(Comment), position)
            .enqueue(object : Callback<JSONObject> {
                val commentDialog = CommentDialog()
                override fun onResponse(
                    call: Call<JSONObject>,
                    response: Response<JSONObject>
                ) {
                    //다음 화면으로 이동
                    //통신성공
                    if (response.code() == 200) {
                        //서버로부터 받은 정보들을 EmailLoginBody 변수에 담아준다
                        commentDialog.connectionSuccess(
                            response.code(),
                            context,
                            response.errorBody()?.string().toString(),
                            sweetAlertDialog
                        )

                    }
                    //통신 실패
                    else if (response.code() == 401 || response.code() == 404 || response.code() == 400 ) {
                        commentDialog.connectionSuccess(
                            response.code(),
                            context,
                            response.errorBody()?.string().toString(),
                            sweetAlertDialog
                        )
                    }
                }
                //서버와 연결 실패
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                    //LoginDialog 를 호출하여 서버와의 연결 실패를 dialog 로 띄운다
                    commentDialog.connectionFail(context, sweetAlertDialog)
                }
            })
    }
}