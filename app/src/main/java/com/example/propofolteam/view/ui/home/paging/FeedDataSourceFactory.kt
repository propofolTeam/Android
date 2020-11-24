package com.junhyuk.daedo.main.bottomItem.home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.propofolteam.data.FeedItemData
import com.example.propofolteam.data.FeedItemResponse

class FeedDataSourceFactory : DataSource.Factory<Int, FeedItemResponse>() {

    private val mutableLiveData: MutableLiveData<PageKeyedDataSource<Int, FeedItemResponse>> = MutableLiveData()

    override fun create(): DataSource<Int, FeedItemResponse> {
        val feedDataSource = FeedDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    internal fun getFeedLiveData(): MutableLiveData<PageKeyedDataSource<Int, FeedItemResponse>>{
        return mutableLiveData
    }

}