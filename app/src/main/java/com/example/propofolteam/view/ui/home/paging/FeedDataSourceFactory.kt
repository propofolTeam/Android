package com.junhyuk.daedo.main.bottomItem.home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.junhyuk.daedo.main.bottomItem.home.data.FeedData

class FeedDataSourceFactory : DataSource.Factory<Int, FeedData>() {

    private val mutableLiveData: MutableLiveData<PageKeyedDataSource<Int, FeedData>> = MutableLiveData()

    override fun create(): DataSource<Int, FeedData> {
        val feedDataSource = FeedDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    internal fun getFeedLiveData(): MutableLiveData<PageKeyedDataSource<Int, FeedData>>{
        return mutableLiveData
    }

}