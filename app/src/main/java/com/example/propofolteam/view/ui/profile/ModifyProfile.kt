package com.example.propofolteam.view.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_intro.*
import kotlinx.android.synthetic.main.fragment_profile.view.profileImg
import kotlinx.android.synthetic.main.modify_profile.*
import kotlinx.android.synthetic.main.modify_profile.view.*


class ModifyProfile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modify_profile, container, false)
        val url =
            "https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300"
        Glide.with(this)
            .load(url)
            .override(400)
            .circleCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(view.profileImg)

        Glide.with(this)
            .load(R.drawable.modify_image)
            .override(130)
            .placeholder(R.mipmap.ic_launcher)
            .into(view.gallery)

        view.gallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*";
            intent.action = Intent.ACTION_GET_CONTENT;
            startActivityForResult(intent, 1)


        }


        return view
    }

    var resolver = activity?.contentResolver
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                var dataUri = data?.data
                try {
                    // 선택한 이미지에서 비트맵 생성
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(resolver, dataUri)
                    // 이미지 표시
                    profileImg.setImageBitmap(bitmap)
                    Log.d("modify","사진변경")
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        }
    }
}
