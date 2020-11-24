package com.example.propofolteam.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_intro.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class IntroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro, container, false)
        view.intent_login.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }
        return view
    }
}