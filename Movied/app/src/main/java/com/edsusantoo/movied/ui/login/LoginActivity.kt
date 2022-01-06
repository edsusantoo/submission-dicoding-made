package com.edsusantoo.movied.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.core.utils.MoviedUtils
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityLoginBinding
import com.edsusantoo.movied.ui.main.MainActivity
import com.edsusantoo.movied.ui.register.RegisterActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.appBar.tvTitle.text = "Login"
        binding.appBar.imgBack.setOnClickListener { finish() }
        initEditText()

        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        binding.btnGmail.setOnClickListener {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }

        binding.togglePassword.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isNotEmpty()) {
                loginViewModel.getUser(binding.edtEmail.text.toString())
                    .observe(this, { user ->
                        if (user != null) {
                            when (user) {
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
                                    when {
                                        password != user.data?.password -> {
                                            Toast.makeText(
                                                this,
                                                "Password wrong",
                                                Toast.LENGTH_SHORT
                                            )
                                                .show()
                                        }
                                        else -> {
                                            user.data?.let {
                                                loginViewModel.updateUser(
                                                    User(
                                                        userId = it.userId,
                                                        username = it.username,
                                                        email = it.email,
                                                        password = it.password,
                                                        isLogin = true
                                                    )
                                                )
                                                startActivity(
                                                    Intent(
                                                        this,
                                                        MainActivity::class.java
                                                    )
                                                )
                                                finish()
                                            }

                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    when {
                                        email != user.data?.email -> {
                                            Toast.makeText(
                                                this,
                                                "Email is not register",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    })
                MoviedUtils.hideKeyboard(this, binding.btnLogin)
            }
        }
    }

    private fun initData() {

    }

    @SuppressLint("CheckResult")
    private fun initEditText() {
        val emailStream = RxTextView.textChanges(binding.edtEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edtPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordNotEmpty(it)
        }

        val invalidStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            { emailInvalid: Boolean, passwordInvalid: Boolean ->
                !emailInvalid && !passwordInvalid
            })
        invalidStream.subscribe {
            if (it) {
                binding.btnLogin.isEnabled = true
                binding.btnLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.green_500))
            } else {
                binding.btnLogin.isEnabled = true
                binding.btnLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_9C))
            }
        }
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordNotEmpty(isNotValid: Boolean) {
        binding.edtPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }
}