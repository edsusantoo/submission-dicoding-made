package com.edsusantoo.movied.ui.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.edsusantoo.movied.R
import com.edsusantoo.movied.ui.login.LoginActivity
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        tvTitle.text = "Sign up"

        val btnLogin = findViewById<TextView>(R.id.btn_login)
        btnLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}