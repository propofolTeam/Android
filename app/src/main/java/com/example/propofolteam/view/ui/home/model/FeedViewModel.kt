package com.junhyuk.daedo.main.bottomItem.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.propofolteam.data.FeedResponseItemData
import com.example.propofolteam.view.ui.home.paging.FeedDataSource
import com.example.propofolteam.view.ui.home.paging.FeedDataSourceFactory

class FeedViewModel : ViewModel() {

    private val feedDataSource = FeedDataSource()

    internal val feedPagedList: LiveData<PagedList<FeedResponseItemData>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int, FeedResponseItemData>>

    init {
        val feedDataSourceFactory = FeedDataSourceFactory()
        liveDataSource = feedDataSourceFactory.getFeedLiveData()

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(feedDataSource.firstPage)
            .build()

        feedPagedList = LivePagedListBuilder(feedDataSourceFactory, config).build()
    }

}