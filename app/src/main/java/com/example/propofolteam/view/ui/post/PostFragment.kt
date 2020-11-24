package com.example.propofolteam.view.ui.post

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.propofolteam.R
import com.example.propofolteam.application.PropofolApplication
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.module.PostDialog
import com.example.propofolteam.module.RotateImage
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.MainBottomActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main_bottom.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragment : Fragment() {

    //이미지 관련 상수
    private val filePick = 100

    private var pdfFile: MultipartBody.Part? = null
    private var fileName: String = ""

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        requireActivity().nav_view.visibility = View.GONE

        view.addFile.setOnClickListener {
            val imageIntent = Intent() //구글 갤러리 접근 intent 변수
            //구글 갤러리 접근
            imageIntent.type = "application/pdf"
            imageIntent.action = Intent.ACTION_GET_CONTENT
            if (imageIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivityForResult(imageIntent, filePick)
            }
        }

        view.postButton.setOnClickListener {

            if (view.question_contents.text.isNotEmpty() && view.question_title.text.isNotEmpty() && pdfFile != null) {

                //로딩 다이얼로그
                val sweetAlertDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
                sweetAlertDialog.progressHelper.barColor = Color.parseColor("#0DE930")
                sweetAlertDialog
                    .setTitleText("로딩 중")
                    .setCancelable(false)
                sweetAlertDialog.show()

                //서버에 보낼 데이터
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

                builder.addFormDataPart("title", view.question_title.text.toString())
                builder.addFormDataPart("content", view.question_contents.text.toString())
                builder.addFormDataPart("file", fileName, pdfFile!!.body())

                val postBody: RequestBody = builder.build()


                //post 서비스
                val postService =
                    (requireActivity().application as PropofolApplication).retrofit.create(Service::class.java)

                postService.post("Bearer ${EmailLoginResponse.instance!!.accessToken}", postBody)
                    .enqueue(object : Callback<JSONObject>{

                        val postDialog = PostDialog()

                        override fun onResponse(
                            call: Call<JSONObject>,
                            response: Response<JSONObject>
                        ) {
                            val intent = Intent(requireContext(), MainBottomActivity::class.java)

                            Log.d("code", "res: ${response.message()}")

                            postDialog.connectionSuccess(
                                response.code(),
                                response.message(),
                                requireContext(),
                                response,
                                intent,
                                sweetAlertDialog
                            )

                        }

                        override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                            postDialog.connectionFail(requireContext(), sweetAlertDialog)
                            Log.d("throw", "data: ${t.printStackTrace()}")
                        }
                    })
            } else {
                Toast.makeText(requireContext(), "제목, 내용, 파일을 확인해주십시오", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    //갤러리에서 넘어온 이미지 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val returnUri: Uri
        val returnCursor: Cursor?

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                filePick -> {

                    returnUri = data?.data!!

                    returnCursor =
                        requireActivity().contentResolver.query(returnUri, null, null, null, null)

                    //이미지 이름
                    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    returnCursor.moveToFirst()
                    fileName = returnCursor.getString(nameIndex)

                    view?.fileName!!.text = fileName

                    returnCursor.close()

                    pdfFile = returnUri.asMultipart(fileName, requireContext().contentResolver)

                    requireView().pdfFile.visibility = View.VISIBLE

                    Log.d("pdf", "data: $pdfFile")

                }

                else -> {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "이미지를 제대로 가져오지 못하였습니다.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

    }

    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return MediaType.parse(contentResolver.getType(this@asMultipart)!!)
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(Okio.source(contentResolver.openInputStream(this@asMultipart)!!))
                    }
                }
                MultipartBody.Part.createFormData(
                    name,
                    it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME)),
                    requestBody
                )
            } else {
                null
            }
        }
    }

}