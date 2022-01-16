package com.edsusantoo.movied.ui.welcomescreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edsusantoo.core.data.Resource
import com.edsusantoo.movied.databinding.ActivityWelcomeBinding
import com.edsusantoo.movied.ui.login.LoginActivity
import com.edsusantoo.movied.ui.main.MainActivity
import com.edsusantoo.movied.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private val welcomeLiveData: WelcomeLiveData by viewModels()
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btnGetStarted.setOnClickListener {
            welcomeLiveData.getAllUser.observe(this, { user ->
                if (user != null) {
                    when (user) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            if (user.data != null && user.data?.size != 0) {
                                var isLogin = false
                                user.data?.map {
                                    if (it.isLogin) {
                                        isLogin = true
                                    }
                                }
                                if (isLogin) {
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                } else {
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                            } else {
                                startActivity(Intent(this, RegisterActivity::class.java))
                                finish()
                            }
                        }
                        is Resource.Error -> {
                            Toast.makeText(this, user.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }
}
