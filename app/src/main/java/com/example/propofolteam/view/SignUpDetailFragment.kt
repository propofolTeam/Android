package com.example.propofolteam.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Attendees.query
import android.provider.CalendarContract.EventDays.query
import android.provider.CalendarContract.Instances.query
import android.provider.CalendarContract.Reminders.query
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContentResolverCompat.query
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.propofolteam.R
import com.example.propofolteam.module.RotateImage
import com.example.propofolteam.workingRetrofit.SignUpRetrofit
import kotlinx.android.synthetic.main.fragment_sign_up_detail.*
import kotlinx.android.synthetic.main.fragment_sign_up_detail.view.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class SignUpDetailFragment : Fragment(){

    //이미지 관련 상수
    private val profileImagePick = 70

    private var userName: String = ""
    private var imageName: String = ""
    private var profileImage: RequestBody? = null
    private var email: String = ""
    private var password: String = ""

    private val rotateImageClass = RotateImage() //이미지 회전
    private val setupRetrofit = SignUpRetrofit() //retrofit setup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_detail, container, false)
        view.sign_up_profile.setOnClickListener {
            val imageIntent = Intent() //구글 갤러리 접근 intent 변수
            //구글 갤러리 접근
            imageIntent.type = "image/*"
            imageIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(imageIntent, profileImagePick)
        }
        if(profileImage == null){

            val bos = ByteArrayOutputStream()
            val drawable = getDrawable(requireContext(),R.drawable.user_profile)
            val bitmapDrawable = drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            profileImage = RequestBody.create(MultipartBody.FORM, bos.toByteArray())
            Log.d("exampletest", "test : $profileImage") //input 스트림
        }

        view.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                userName = name.text.toString()
                checkNameMsg()
            }

            override fun afterTextChanged(s: Editable) {
                userName = name.text.toString()
                checkNameMsg()
            }
        })
        view.sign_up_btn.setOnClickListener {
            email  = (activity as MainActivity).email
            password = (activity as MainActivity).password
            setupRetrofit.setupRetrofit(email, password, userName, profileImage, imageName, requireActivity().application, requireContext())
        }

        view.sign_up_detail_have_account.setOnClickListener {
            findNavController().navigate(R.id.action_signUpDetailFragment_to_loginFragment)
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
                profileImagePick -> {

                    //이미지 정보
                    returnUri = data?.data!!

                    //이미지 파일 받아오기
                    val inputStream = requireContext().contentResolver.openInputStream(returnUri) //input 스트림
                    var bm: Bitmap = BitmapFactory.decodeStream(inputStream) //비트맵 변환
                    bm = rotateImageClass.rotateImage(data.data!!, bm, requireContext().contentResolver) //이미지 회전
                    val bos = ByteArrayOutputStream()
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    profileImage = RequestBody.create(MultipartBody.FORM, bos.toByteArray())
                    inputStream?.close()

                    //화면에 이미지 표시
                    Glide.with(this)
                        .load(bm)
                        .thumbnail(Glide.with(requireContext()).load(R.raw.loading))
                        .override(1000)
                        .transform(CenterCrop(), RoundedCorners(1000000000))
                        .into(sign_up_profile)

                    returnCursor = requireContext().contentResolver.query(returnUri, null, null, null, null)

                    //이미지 이름
                    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    returnCursor.moveToFirst()
                    imageName = returnCursor.getString(nameIndex)

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

    //권한 허용 확인
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {

            val length = permissions.size

            for (i in 0..length) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "권환 허용" + permissions[i])
                }
            }
        }
    }
    private fun checkNameMsg() {
        if (name.text.toString().isNotBlank()) {
            view?.name?.setBackgroundResource(R.drawable.edit_login)
            view?.sign_up_btn?.isEnabled = true
        } else {
            view?.name?.setBackgroundResource(R.drawable.email_false)
            view?.name?.isEnabled = false
        }
    }
}