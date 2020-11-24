package com.junhyuk.daedo.main.bottomItem.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.propofolteam.R
import com.junhyuk.daedo.main.bottomItem.home.data.FeedData
import com.junhyuk.daedo.main.bottomItem.home.paging.DiffUtilCallBack


class FeedAdapter(private val context: Context) :
    PagedListAdapter<FeedData, FeedAdapter.Holder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val feedData = getItem(position) //서버에서 feedData 받아오기(pagingLibrary)

    }

    // 'onBindViewHolder'에서 쓰일 xml 아이템 뷰 선언
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}