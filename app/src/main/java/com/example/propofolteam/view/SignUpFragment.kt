package com.example.propofolteam.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.propofolteam.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.android.synthetic.main.fragment_sign_up_detail.view.*
import java.util.regex.Matcher

class SignUpFragment : Fragment() {
    private var checkEmail: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        view.intent_sign_up_detail.setOnClickListener {
            (activity as MainActivity).email = view.sign_up_email.text.toString()
            (activity as MainActivity).password = view.sign_up_password.text.toString()
            findNavController().navigate(R.id.action_signUpFragment_to_signUpDetailFragment)
        }
        view.sign_up_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkEmail()
            }

            override fun afterTextChanged(s: Editable) {
                checkEmail()
            }
        })
        return view

    }

    private fun isEmail(email: String): Boolean {
        var returnValue = false
        val pattern = Patterns.EMAIL_ADDRESS
        val m: Matcher = pattern.matcher(email)
        if (m.matches()) {
            returnValue = true
        }
        return returnValue
    }

   private fun checkEmail() {
        if (isEmail(sign_up_email.text.toString())) {
            view?.sign_up_email?.setBackgroundResource(R.drawable.edit_login)
            checkEmail = true
                checkButton(checkEmail)
             } else {
            view?.sign_up_email?.setBackgroundResource(R.drawable.email_false)
            checkEmail = false
            checkButton(checkEmail)
        }
    }
    private fun checkButton(checkEmail: Boolean) {

        this.checkEmail = checkEmail


        if (checkEmail) {

            view?.intent_sign_up_detail?.setBackgroundResource(R.drawable.btn_shape)
            view?.intent_sign_up_detail?.isEnabled = true

        } else {

            view?.intent_sign_up_detail?.setBackgroundResource(R.drawable.btn_shape)
            view?.intent_sign_up_detail?.isEnabled = false

        }
    }

}