package com.example.propofolteam.data

data class EmailLoginResponse(
    var accessToken: String? = "",
    var refreshToken: String? = "",
    var tokenType: String? = ""
){
    companion object{
        var instance : EmailLoginResponse? = null
    }
}