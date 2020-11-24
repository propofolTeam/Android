package com.junhyuk.daedo.main.bottomItem.home.paging

import androidx.recyclerview.widget.DiffUtil
import com.example.propofolteam.data.FeedResponseItemData

class DiffUtilCallBack : DiffUtil.ItemCallback<FeedResponseItemData>() {
    override fun areItemsTheSame(oldItem: FeedResponseItemData, newItem: FeedResponseItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedResponseItemData, newItem: FeedResponseItemData): Boolean {
        return oldItem == newItem
    }

}