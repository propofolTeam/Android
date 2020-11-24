package com.example.propofolteam.view.ui.home.paging

import androidx.paging.PageKeyedDataSource
import com.example.propofolteam.data.FeedData
import com.example.propofolteam.data.FeedResponseItemData
import com.junhyuk.daedo.main.bottomItem.home.workinRetrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedDataSource : PageKeyedDataSource<Int, FeedResponseItemData>() {

    internal val firstPage = 1
    private val retrofitClient = RetrofitClient()
    private var feedDataList = ArrayList<FeedResponseItemData>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FeedResponseItemData>
    ) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(firstPage)
                ?.enqueue(object : Callback<FeedData> {
                    override fun onResponse(
                        call: Call<FeedData>,
                        response: Response<FeedData>
                    ) {
                        if (response.body() != null) {
                            callback.onResult(response.body()!!.response, null, firstPage + 1)
                        }
                    }

                    override fun onFailure(call: Call<FeedData>, t: Throwable) {

                    }
                })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedResponseItemData>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(params.key)
                ?.enqueue(object : Callback<FeedData> {
                    override fun onResponse(
                        call: Call<FeedData>,
                        response: Response<FeedData>
                    ) {
                        val key: Int? = if (params.key > 1) {
                            params.key - 1
                        } else {
                            null
                        }

                        callback.onResult(response.body()!!.response, key)
                    }

                    override fun onFailure(call: Call<FeedData>, t: Throwable) {

                    }
                })
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedResponseItemData>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(params.key)
                ?.enqueue(object : Callback<FeedData> {
                    override fun onResponse(
                        call: Call<FeedData>,
                        response: Response<FeedData>
                    ) {


                        if (feedDataList.size % 15 == 0) {
                            val key: Int? = if (params.key <= 1) {
                                params.key + 1
                            } else {
                                null
                            }


                            callback.onResult(response.body()!!.response, key)

                        }
                    }

                    override fun onFailure(call: Call<FeedData>, t: Throwable) {

                    }
                })
        }
    }
}