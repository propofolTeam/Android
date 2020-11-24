package com.example.propofolteam.data

data class CommentResponse(
    var id: Int = 0,
    var comment: String? = "",
    var writer: String = "",
    var userId: Int = 0,
    var image: String = ""
)
