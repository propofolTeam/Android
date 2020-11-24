package com.example.propofolteam.data

data class UserProfileResponse(
    var email : String? = "",
    var name : String? = "",
    var image : String? = "",
    var totalElements : Int = 0,
    var totalPage : Int = 0,
    var posts : ArrayList<UserPostsResponse>
){
    companion object{
        var instance : UserProfileResponse? = null
    }
}
