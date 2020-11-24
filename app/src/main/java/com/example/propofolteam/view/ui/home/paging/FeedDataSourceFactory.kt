package com.example.propofolteam.view.ui.home.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.propofolteam.data.FeedResponseItemData
import com.example.propofolteam.view.ui.home.paging.FeedDataSource

class FeedDataSourceFactory : DataSource.Factory<Int, FeedResponseItemData>() {

    private val mutableLiveData: MutableLiveData<PageKeyedDataSource<Int, FeedResponseItemData>> = MutableLiveData()

    override fun create(): DataSource<Int, FeedResponseItemData> {
        val feedDataSource = FeedDataSource()
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource
    }

    internal fun getFeedLiveData(): MutableLiveData<PageKeyedDataSource<Int, FeedResponseItemData>>{
        return mutableLiveData
    }

}