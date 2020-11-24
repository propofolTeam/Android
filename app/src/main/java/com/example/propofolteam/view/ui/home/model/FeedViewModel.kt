package com.example.propofolteam.view.ui.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.propofolteam.data.FeedItemResponse
import com.junhyuk.daedo.main.bottomItem.home.paging.FeedDataSource
import com.junhyuk.daedo.main.bottomItem.home.paging.FeedDataSourceFactory

class FeedViewModel : ViewModel() {

    private val feedDataSource = FeedDataSource()

    internal val feedPagedList: LiveData<PagedList<FeedItemResponse>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int, FeedItemResponse>>

    init {
        val feedDataSourceFactory = FeedDataSourceFactory()
        liveDataSource = feedDataSourceFactory.getFeedLiveData()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()

        feedPagedList = LivePagedListBuilder(feedDataSourceFactory, config).build()
    }

}