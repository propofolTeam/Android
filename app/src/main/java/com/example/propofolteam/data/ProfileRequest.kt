package com.example.propofolteam.data

data class ProfileRequest(
    val Authorization : String? = "",
    val userId : Long? = null,
    val page : Int? = null
)
