package com.example.propofolteam.view.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val url =
            "https://lh6.ggpht.com/9SZhHdv4URtBzRmXpnWxZcYhkgTQurFuuQ8OR7WZ3R7fyTmha77dYkVvcuqMu3DLvMQ=w300"
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.mipmap.ic_launcher)
            .into(view.profileImg)

        view.modifyButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_modifyProfile)
        }

        return view
    }
}