package com.edsusantoo.movied.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.edsusantoo.movied.R
import com.edsusantoo.movied.ui.register.RegisterActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvTitle = findViewById<TextView>(R.id.tv_title)
        tvTitle.text = "Login"

        val btnRegister = findViewById<TextView>(R.id.btn_signup)
        btnRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}