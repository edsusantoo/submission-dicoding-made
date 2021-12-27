package com.edsusantoo.movied.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityLoginBinding
import com.edsusantoo.movied.ui.main.MainActivity
import com.edsusantoo.movied.ui.register.RegisterActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.tvTitle.text = "Login"

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
}