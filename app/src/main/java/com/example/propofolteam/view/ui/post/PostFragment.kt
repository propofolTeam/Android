package com.example.propofolteam.view.ui.post

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.activity_main_bottom.*
import kotlinx.android.synthetic.main.fragment_post.view.*

class PostFragment : Fragment() {

    //이미지 관련 상수
    private val filePick = 100

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
}