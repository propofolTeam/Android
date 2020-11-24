package com.example.propofolteam.view.ui.home.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.propofolteam.data.FeedItemData
import com.example.propofolteam.data.FeedItemResponse

class DiffUtilCallBack : DiffUtil.ItemCallback<FeedItemResponse>() {
    override fun areItemsTheSame(oldItem: FeedItemResponse, newItem: FeedItemResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItemResponse, newItem: FeedItemResponse): Boolean {
        return oldItem == newItem
    }

}