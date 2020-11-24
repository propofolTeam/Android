package com.example.propofolteam.view.ui.post

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.example.propofolteam.R
import com.example.propofolteam.module.RotateImage
import kotlinx.android.synthetic.main.activity_main_bottom.*
import kotlinx.android.synthetic.main.fragment_post.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class PostFragment : Fragment() {

    //이미지 관련 상수
    private val filePick = 100

    private val rotateImageClass = RotateImage() //이미지 회전
    private var pdfFile: RequestBody? = null
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

                    pdfFile = RequestBody.create(MultipartBody.FORM, returnUri.toFile())

                    returnCursor =
                        requireActivity().contentResolver.query(returnUri, null, null, null, null)

                    //이미지 이름
                    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    returnCursor.moveToFirst()
                    fileName = returnCursor.getString(nameIndex)

                    view?.fileName!!.text = fileName

                    returnCursor.close()

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

}