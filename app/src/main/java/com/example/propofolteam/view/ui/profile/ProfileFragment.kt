package com.example.propofolteam.view.ui.profile

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.propofolteam.R
import com.example.propofolteam.application.PropofolApplication
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.PostResponse
import com.example.propofolteam.data.UserPostsResponse
import com.example.propofolteam.data.UserProfileResponse
import com.example.propofolteam.network.BaseUrl
import com.example.propofolteam.network.Service
import com.example.propofolteam.view.ui.profile.adapter.ProfileRecyclerViewAdapter
import com.example.propofolteam.workingRetrofit.ProfileRetrofit
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {
    private lateinit var commentAdapter: ProfileRecyclerViewAdapter
    private var commentList = arrayListOf<UserPostsResponse>()
    private val baseUrl = BaseUrl()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        commentAdapter = ProfileRecyclerViewAdapter(requireContext(), commentList,view){}
        view.profile_post.adapter = commentAdapter
        view.user_profile_image


        val profileService =
            (requireActivity().application as com.example.propofolteam.application.PropofolApplication).retrofit.create(
                Service::class.java
            )
        Log.d("test","안드 씨발련아")
        val token = EmailLoginResponse.instance!!.accessToken
        profileService.getUserProfile("0","Bearer $token")
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    //서버로부터 받은 정보들을 EmailLoginBody 변수에 담아준다
                    UserProfileResponse.instance = response.body()
                    Glide.with(requireContext())
                        .load("${baseUrl.BaseURL}/image/${UserProfileResponse.instance!!.image}")
                        .circleCrop()
                        .into(view.user_profile_image)
                    commentAdapter.setCommentList(UserProfileResponse.instance!!.posts)
                    view.profile_user_name.text = UserProfileResponse.instance!!.name
                }
                //서버와 연결 실패
                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {

                }
            })


        return view
    }

}