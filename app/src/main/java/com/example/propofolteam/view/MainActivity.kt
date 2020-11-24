package com.example.propofolteam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propofolteam.R

class MainActivity : AppCompatActivity() {

    internal var email = ""
    internal var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}