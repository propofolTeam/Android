package com.example.propofolteam.module

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import cn.pedant.SweetAlert.SweetAlertDialog
import kotlinx.coroutines.delay

class LoginDialog {
    internal fun connectionSuccess(
        responseCode: Int,
        context: Context,
        responseBody: String,
        intent: Intent,
        sweetAlertDialog: SweetAlertDialog
    ) {
        //로그인 성공
        when (responseCode) {
            200 -> {
                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("로그인 성공")
                    .setConfirmClickListener {
                        ContextCompat.startActivity(context, intent, null)
                        (context as Activity).finish()
                        ActivityCompat.finishAffinity(context)
                        dialog.dismiss()
                    }
                    .show()

            }
            //로그인 실패
            400 -> {
                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("로그인에 실패했습니다")
                    .setConfirmClickListener {
                        dialog.dismiss()
                    }
                    .setContentText(responseBody)
                    .show()
            }
            404 -> {
                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("이메일이나 비밀번호를 다시 확인해주세요")
                    .setConfirmClickListener {
                        dialog.dismiss()
                    }
                    .setContentText(responseBody)
                    .show()
            }

            else -> {
            }
        }
    }
    //서버 통신 실패
    fun connectionFail(context: Context, sweetAlertDialog: SweetAlertDialog) {

        sweetAlertDialog.dismiss()
        val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

        dialog.setCancelable(false)

        dialog.setTitleText("서버 통신에 실패하였습니다.")
            .setConfirmClickListener {
                dialog.dismiss()
            }
            .setContentText("네트워크 연결을 확인해 주세요.")
            .show()
    }
}