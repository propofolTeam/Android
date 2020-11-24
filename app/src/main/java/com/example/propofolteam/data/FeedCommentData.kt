package com.junhyuk.daedo.main.bottomItem.home.data

data class FeedCommentData(
    val comment_id: Int,
    val owner: FeedOwnerData,
    val content: String,
    val created_at: String
)