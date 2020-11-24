package com.example.propofolteam.view.ui.profile.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.navigation.fragment.findNavController
import com.example.propofolteam.R
import com.example.propofolteam.data.UserProfileResponse


//recyclerview adapter
class ProfileRecyclerViewAdapter(private val context: Context, private val postList : ArrayList<UserProfileResponse>, val view : View?,
                                 val itemClick: (UserProfileResponse) -> Unit) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(postList[position])

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //유저 게시물1
        private var userPost = itemView.findViewById<ImageView>(R.id.user_post1)

        fun bind (profile: UserProfileResponse) {
            Glide.with(context)
                .load(profile.image?.first())
                .centerCrop()
                .into(userPost)

        }
    }
}