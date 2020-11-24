package com.example.propofolteam.data

import retrofit2.http.Multipart

data class ModifyProfileResquest(
    val Authorization : String? = "",
    val name : String? = "",
    val image : Multipart)