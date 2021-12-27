package com.edsusantoo.movied.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityRegisterBinding
import com.edsusantoo.movied.ui.login.LoginActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBar.tvTitle.text = "Sign up"

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}