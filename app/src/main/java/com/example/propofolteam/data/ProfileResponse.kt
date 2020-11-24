package com.example.propofolteam.data

data class ProfileResponse(
    val email : String?= "",
    val name : String?="",
    val image : String?= "",
    val isMine : Boolean?= null,
    val totalElements : Int?= null,
    val totalPage : Int?= null,
    val posts : Array<String> ?= null
){
    companion object{
        var instance : ProfileResponse? = null
    }
}
