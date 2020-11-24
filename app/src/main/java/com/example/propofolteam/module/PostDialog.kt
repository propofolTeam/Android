package com.example.propofolteam.module

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import org.json.JSONObject
import retrofit2.Response

class PostDialog {

    internal fun connectionSuccess(
        responseCode: Int,
        responseMsg: String,
        context: Context,
        responseBody: Response<JSONObject>,
        intent: Intent,
        sweetAlertDialog: SweetAlertDialog
    ) {
        //통신 성공
        when (responseCode) {
            200 -> {
                sweetAlertDialog.dismiss()

                val dialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText(responseMsg)
                    .setConfirmClickListener {
                        startActivity(context, intent, null)
                        (context as Activity).finish()
                        ActivityCompat.finishAffinity(context)
                        dialog.dismiss()
                    }
                    .show()

            }

            400 -> {
                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("서버 통신에 실패하였습니다.")
                    .setConfirmClickListener {
                        dialog.dismiss()
                    }
                    .setContentText(responseBody.errorBody()?.string())
                    .show()
            }

            401 -> {

                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("서버 통신에 실패하였습니다.")
                    .setConfirmClickListener {
                        dialog.dismiss()
                    }
                    .setContentText(responseBody.errorBody()?.string())
                    .show()
            }

            else -> {
                sweetAlertDialog.dismiss()
                val dialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)

                dialog.setCancelable(false)

                dialog.setTitleText("서버 통신에 실패하였습니다.")
                    .setConfirmClickListener {
                        dialog.dismiss()
                    }
                    .setContentText("서버 관리자에게 문의하세요 ${responseBody.errorBody()?.string()}")
                    .show()
            }
        }
    }

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