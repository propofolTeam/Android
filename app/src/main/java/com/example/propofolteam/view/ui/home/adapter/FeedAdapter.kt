package com.example.propofolteam.view.ui.home.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.propofolteam.R
import com.example.propofolteam.data.FeedItemData
import com.example.propofolteam.data.FeedItemResponse
import com.example.propofolteam.module.FeedTime
import com.example.propofolteam.network.BaseUrl
import com.example.propofolteam.view.ui.home.paging.DiffUtilCallBack
import kotlinx.android.synthetic.main.post_item.view.*


class FeedAdapter(private val context: Context) :
    PagedListAdapter<FeedItemResponse, FeedAdapter.Holder>(DiffUtilCallBack()) {

    //모듈 정의
    private val feedPostTime = FeedTime()
    private val baseUrl = BaseUrl()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val feedData = getItem(position) //서버에서 feedData 받아오기(pagingLibrary)

        holder.title.text = feedData!!.title
        holder.content.visibility = View.GONE
        holder.writer.text = feedData.writer

        holder.itemView.setOnClickListener {
            FeedItemData.postId = position + 1
            holder.itemView.findNavController().navigate(R.id.action_navigation_home_to_feedDetailFragment)
        }

        Log.d("urlTest", "data: ${baseUrl.BaseURL}/image/${feedData.image}")

        Glide.with(context)
            .load("${baseUrl.BaseURL}/image/${feedData.image}")
            .override(1000)
            .transform(CenterCrop(), RoundedCorners(1000000000))
            .into(holder.image)

        holder.createdAt.text = feedPostTime.calFeedTime(feedData.createdAt)
        holder.commentCount.text = feedData.commentCount.toString()
    }

    // 'onBindViewHolder'에서 쓰일 xml 아이템 뷰 선언
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView = itemView.postTitle
        var content: TextView = itemView.postContent
        var writer: TextView = itemView.postname
        var image: ImageView = itemView.imageView2
        var createdAt: TextView = itemView.writetime
        var commentCount: TextView = itemView.commentCount

    }

}