package com.example.propofolteam.view

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        view.intro_have_account.setOnClickListener{
            findNavController().navigate(R.id.action_introFragment_to_signUpFragment)
        }
        checkSelfPermission()
        return view
    }
    private fun checkSelfPermission() {

        var temp = ""

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " "
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }

        if (!TextUtils.isEmpty(temp)) {
            ActivityCompat.requestPermissions(requireContext() as Activity, temp.trim().split(" ").toTypedArray(), 1)
        }

    }
}