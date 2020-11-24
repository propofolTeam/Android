package com.example.propofolteam.data

data class PostResponse(
    var postId : Int = 0,
    var title : String? = "",
    var content : String? = "",
    var writer : String? = "",
    var createdAt : String? = "",
    var image: String = "",
    var mine : Boolean,
    var fileId : String? = "",
    var fileName : String = "",
    var comments : ArrayList<CommentResponse>
){
    companion object{
        var instance : PostResponse? = null
    }
}
