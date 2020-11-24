
package com.example.propofolteam.data

data class FeedItemData(
    val response: List<FeedItemResponse>
){
    companion object{
        var postId: Int = 0
    }
}