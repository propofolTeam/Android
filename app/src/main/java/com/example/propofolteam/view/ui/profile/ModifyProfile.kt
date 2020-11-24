package com.example.propofolteam.view.ui.profile

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ModifyProfile : Fragment() {
    private val GET_GALLERY_IMAGE = 200
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
            .circleCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(view.profileImg)

        view.modifyButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivity(intent)
        }

        return view
    }

}
