package com.junhyuk.daedo.main.bottomItem.home.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.junhyuk.daedo.main.bottomItem.home.data.FeedData
import com.junhyuk.daedo.main.bottomItem.home.workinRetrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedDataSource : PageKeyedDataSource<Int, FeedData>() {

    internal val firstPage = 1
    private val retrofitClient = RetrofitClient()
    private var feedDataList = ArrayList<FeedData>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FeedData>
    ) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(firstPage)
                ?.enqueue(object : Callback<List<FeedData>> {
                    override fun onResponse(
                        call: Call<List<FeedData>>,
                        response: Response<List<FeedData>>
                    ) {
                        if (response.body() != null) {
                            feedDataList = response.body() as ArrayList<FeedData>
                            Log.d("안드 스발것", "data: $feedDataList")
                            callback.onResult(response.body()!!, null, firstPage + 1)
                        }
                    }

                    override fun onFailure(call: Call<List<FeedData>>, t: Throwable) {

                    }
                })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedData>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(params.key)
                ?.enqueue(object : Callback<List<FeedData>> {
                    override fun onResponse(
                        call: Call<List<FeedData>>,
                        response: Response<List<FeedData>>
                    ) {
                        val key: Int? = if (params.key > 1) {
                            params.key - 1
                        } else {
                            null
                        }

                        callback.onResult(response.body()!!, key)
                    }

                    override fun onFailure(call: Call<List<FeedData>>, t: Throwable) {

                    }
                })
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedData>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed(params.key)
                ?.enqueue(object : Callback<List<FeedData>> {
                    override fun onResponse(
                        call: Call<List<FeedData>>,
                        response: Response<List<FeedData>>
                    ) {


                        if (feedDataList.size % 15 == 0) {
                            val key: Int? = if (params.key <= 1) {
                                params.key + 1
                            } else {
                                null
                            }


                            callback.onResult(response.body()!!, key)

                        }
                    }

                    override fun onFailure(call: Call<List<FeedData>>, t: Throwable) {

                    }
                })
        }
    }
}