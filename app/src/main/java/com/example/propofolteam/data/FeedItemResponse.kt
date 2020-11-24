package com.example.propofolteam.data

data class FeedItemResponse(
    val commentCount: Int,
    val createdAt: String,
    val id: Int,
    val image: String,
    val title: String,
    val userId: Int,
    val writer: String
)