package com.example.propofolteam.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.propofolteam.R
import com.example.propofolteam.workingRetrofit.LoginRetrofit
import kotlinx.android.synthetic.main.fragment_intro.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(){
    //사용자의 이메일을 받을 변수
    private var email: String = ""
    //사용자의 비밀번호를 받을 변수
    private var password: String = ""
    private var loginRetrofit = com.example.propofolteam.workingRetrofit.LoginRetrofit()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.login_have_account.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        view.login_btn.setOnClickListener {
            //사용자의 이메일을 Email 변수에 담아준다
            email = view.login_email.text.toString()
            //입력받은 비밀번호를 PW 변수에 담아준다
            password = view.login_password.text.toString()
            //사용자 비밀번호 암호화
            //getEmailLogin 클래스로 사용자가 입력한 값 전달
            loginRetrofit.getEmailLogin(email, password, requireActivity().application, requireContext())
        }
        return view
    }
}