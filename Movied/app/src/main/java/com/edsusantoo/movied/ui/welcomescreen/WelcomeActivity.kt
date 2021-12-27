package com.edsusantoo.movied.ui.welcomescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityWelcomeBinding
import com.edsusantoo.movied.ui.login.LoginActivity
import com.edsusantoo.movied.ui.register.RegisterActivity
import com.google.android.material.button.MaterialButton

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}