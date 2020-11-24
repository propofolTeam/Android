package com.example.propofolteam.view

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.propofolteam.R
import com.example.propofolteam.application.PropofolApplication
import com.example.propofolteam.data.*
import com.example.propofolteam.network.BaseUrl
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.ui.home.adapter.CommentRecyclerviewAdapter
import com.example.propofolteam.workingRetrofit.CommentRetrofit
import kotlinx.android.synthetic.main.fragment_feed_detail.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream


class FeedDetailFragment :Fragment(){
    private lateinit var commentAdapter: CommentRecyclerviewAdapter
    private var commentList = arrayListOf<CommentResponse>()
    private val baseUrl = BaseUrl()

    var fileName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed_detail, container, false)

        var comment = ""
        val sendComment = CommentRetrofit()

        commentAdapter = CommentRecyclerviewAdapter(requireContext(), commentList, view){}
        view.comment_recycler_view.adapter = commentAdapter
        view.commentPost.setOnClickListener {
            comment = view.editText.text.toString()
            sendComment.sendComment(
                comment,
                requireActivity().application,
                requireContext(),
                FeedItemData.postId
            )
        }

        //AccessToken 변수에 저장
        val token: String? = EmailLoginResponse.instance!!.accessToken

        val postService =
            (requireActivity().application as PropofolApplication).retrofit.create(
                Service::class.java
            )

        postService.getPost("Bearer $token", FeedItemData.postId)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    if (response.code() == 200) {
                        PostResponse.instance = response.body()
                        Log.d("responseBody", "responseBody${response.body()}")
                        commentAdapter.setCommentList(PostResponse.instance!!.comments)

                        fileName = PostResponse.instance!!.fileName

                        Glide
                            .with(requireContext())
                            .load("${baseUrl.BaseURL}/image/${PostResponse.instance!!.image}")
                            .override(1000)
                            .transform(CenterCrop(), RoundedCorners(1000000000))
                            .into(view.imageView2)

                        view.postname.text = PostResponse.instance!!.writer
                        view.postTitle.text = PostResponse.instance!!.title
                        view.postContent.text = PostResponse.instance!!.content
                        view.fileName.text = PostResponse.instance!!.fileName
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.d("responseBody", "data: ${t.message}")
                    Log.d("responseBody", "data: ${t.cause}")
                }
            })

        view.fileDownload.setOnClickListener {
            postService.downloadPdf(
                "Bearer $token",
                FeedItemData.postId.toLong(),
                PostResponse.instance!!.fileName
            )
                .enqueue(object : Callback<PdfResponse> {
                    override fun onResponse(
                        call: Call<PdfResponse>,
                        response: Response<PdfResponse>
                    ) {

                    }

                    override fun onFailure(call: Call<PdfResponse>, t: Throwable) {

                    }
                })
        }

        return view
    }


}