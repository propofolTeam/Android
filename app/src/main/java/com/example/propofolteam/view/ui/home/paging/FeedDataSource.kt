package com.example.propofolteam.view.ui.home.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.propofolteam.data.EmailLoginResponse
import com.example.propofolteam.data.FeedItemData
import com.example.propofolteam.data.FeedItemResponse
import com.example.propofolteam.view.ui.home.workinRetrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedDataSource : PageKeyedDataSource<Int, FeedItemResponse>() {

    internal val firstPage = 0
    private val retrofitClient = RetrofitClient()
    private var feedDataList = ArrayList<FeedItemResponse>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FeedItemResponse>
    ) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed("Bearer ${EmailLoginResponse.instance!!.accessToken}",firstPage)
                ?.enqueue(object : Callback<FeedItemData> {
                    override fun onResponse(
                        call: Call<FeedItemData>,
                        response: Response<FeedItemData>
                    ) {
                        if (response.body() != null) {
                            callback.onResult(response.body()!!.response, null, firstPage + 1)
                            Log.d("pageData", "data: ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<FeedItemData>, t: Throwable) {
                        Log.d("page", "data: ${t.cause}")
                        Log.d("page", "data: ${t.message}")
                    }
                })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItemResponse>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed("Bearer ${EmailLoginResponse.instance!!.accessToken}",params.key)
                ?.enqueue(object : Callback<FeedItemData> {
                    override fun onResponse(
                        call: Call<FeedItemData>,
                        response: Response<FeedItemData>
                    ) {
                        val key: Int? = if (params.key > 1) {
                            params.key - 1
                        } else {
                            null
                        }

                        callback.onResult(response.body()!!.response, key)
                    }

                    override fun onFailure(call: Call<FeedItemData>, t: Throwable) {

                    }
                })
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FeedItemResponse>) {
        CoroutineScope(IO).launch {
            retrofitClient
                .getInstance()
                ?.getApi()
                ?.requestFeed("Bearer ${EmailLoginResponse.instance!!.accessToken}",params.key)
                ?.enqueue(object : Callback<FeedItemData> {
                    override fun onResponse(
                        call: Call<FeedItemData>,
                        response: Response<FeedItemData>
                    ) {


                        if (feedDataList.size % 10 == 0) {
                            val key: Int? = if (params.key <= 1) {
                                params.key + 1
                            } else {
                                null
                            }


                            callback.onResult(response.body()!!.response, key)

                        }
                    }

                    override fun onFailure(call: Call<FeedItemData>, t: Throwable) {

                    }
                })
        }
    }
}