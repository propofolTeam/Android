package com.example.propofolteam.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.button.setOnClickListener {
            startActivity(Intent(requireContext(), MainBottomActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}