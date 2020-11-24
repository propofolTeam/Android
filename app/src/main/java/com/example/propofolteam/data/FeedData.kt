package com.junhyuk.daedo.main.bottomItem.home.data

data class FeedData (
    val id: Int,
    val owner: FeedOwnerData,
    val title: String,
    val content: String,
    val comment: String,
    val image_urls: ArrayList<String>,
    val liked_people: ArrayList<String>,
    var like_count: Int,
    val view_count: Int,
    val comment_count: Int,
    val tag: ArrayList<String>,
    val created_at: String,
    val comment_preview: ArrayList<FeedCommentData>
)