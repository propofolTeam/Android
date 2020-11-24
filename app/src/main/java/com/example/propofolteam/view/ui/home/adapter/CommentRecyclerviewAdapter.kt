package com.example.propofolteam.view.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.propofolteam.R
import com.example.propofolteam.data.CommentResponse
import com.example.propofolteam.data.PostResponse
import com.example.propofolteam.network.BaseUrl


class CommentRecyclerviewAdapter(
    private val context: Context, private var commentList: ArrayList<CommentResponse>, val view: View?,
    val itemClick: (CommentResponse) -> Unit

) : RecyclerView.Adapter<CommentRecyclerviewAdapter.Holder>() {

    private val baseUrl = BaseUrl()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.comment_recycler_view_item, parent, false)
        return Holder(view)
    }
    fun setCommentList(list : ArrayList<CommentResponse>){
        commentList = list
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d("e","e")
        holder.bind(commentList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //유저 프로필 사진
        private val userProfile = itemView.findViewById<ImageView>(R.id.user_profile)

        //댓글 작성자
        private val userName = itemView.findViewById<TextView>(R.id.user_name)

        //댓글 내용
        private val commentContent = itemView.findViewById<TextView>(R.id.comment_content)

        fun bind(Comment: CommentResponse) {
            //댓글 작성자 프로필 이미지를 넣어준다.

                Glide.with(context)
                    .load("${baseUrl.BaseURL}/image/${Comment.image}")
                    .transform(CenterCrop(), RoundedCorners(1000000000))
                    .into(userProfile)

            userName?.text = Comment.writer
            commentContent?.text = Comment.comment


            //댓글 내용
          //  commentContent?.text = Comment.comments
            //Log.d("comment","comment : ${Comment.comment}")
            Log.d("comment","comment : ${Comment.image}")
            Log.d("comment","comment : ${Comment.writer}")
            //뎃글 작성자

        }
    }
}