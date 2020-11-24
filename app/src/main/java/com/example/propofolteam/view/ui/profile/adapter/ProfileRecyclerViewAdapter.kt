package com.example.propofolteam.view.ui.profile.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import androidx.navigation.fragment.findNavController
import com.example.propofolteam.R
import com.example.propofolteam.data.CommentResponse
import com.example.propofolteam.data.UserPostsResponse
import com.example.propofolteam.data.UserProfileResponse
import com.example.propofolteam.network.BaseUrl


//recyclerview adapter
class ProfileRecyclerViewAdapter(private val context: Context, private var postList : ArrayList<UserPostsResponse>, val view : View?,
                                 val itemClick: (UserProfileResponse) -> Unit) : RecyclerView.Adapter<ProfileRecyclerViewAdapter.Holder>(){
    private val baseUrl = BaseUrl()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.profile_post_recyclerview_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
    fun setCommentList(list : ArrayList<UserPostsResponse>){
        postList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(postList[position])

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //유저 게시물1
        private var userProfile = itemView.findViewById<ImageView>(R.id.user_profile)
        private var userName = itemView.findViewById<TextView>(R.id.user_name)
        private var title = itemView.findViewById<TextView>(R.id.comment_content)
        private var count = itemView.findViewById<TextView>(R.id.textView3)
        fun bind (profile: UserPostsResponse) {
            Glide.with(context)
                .load("${baseUrl.BaseURL}/image/${profile.image}")
                .circleCrop()
                .into(userProfile)
            userName.text = profile.writer
            title.text = profile.title
            count.text = profile.commentCount.toString()
        }
    }
}