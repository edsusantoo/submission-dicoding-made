package com.edsusantoo.movied.ui.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edsusantoo.movied.R
import com.edsusantoo.movied.ui.login.LoginActivity
import com.edsusantoo.movied.ui.register.RegisterActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnGetStarted = findViewById<MaterialButton>(R.id.btn_get_started)
        btnGetStarted.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}