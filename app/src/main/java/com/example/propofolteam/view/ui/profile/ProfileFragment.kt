package com.example.propofolteam.view.ui.profile

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
    private val postListRetrofit = ProfileRetrofit()
    private lateinit var commentAdapter: ProfileRecyclerViewAdapter
    private var commentList = arrayListOf<UserProfileResponse>()
    private val baseUrl = BaseUrl()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        postListRetrofit.getProfile(
            requireActivity().application,
            requireContext()
        )
        commentAdapter = ProfileRecyclerViewAdapter(requireContext(), commentList,view){}
        view.profile_post.adapter = commentAdapter
        view.user_profile_image

        Glide.with(requireContext())
            .load("${baseUrl.BaseURL}/image/${UserProfileResponse.instance!!.image}")
            .centerCrop()
            .into(view.user_profile_image)


        return view
    }

}