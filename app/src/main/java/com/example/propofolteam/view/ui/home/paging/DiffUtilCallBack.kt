package com.junhyuk.daedo.main.bottomItem.home.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.propofolteam.data.FeedData

class DiffUtilCallBack : DiffUtil.ItemCallback<FeedData>() {
    override fun areItemsTheSame(oldItem: FeedData, newItem: FeedData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedData, newItem: FeedData): Boolean {
        return oldItem == newItem
    }

}