package com.edsusantoo.movied.ui.register

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
import com.edsusantoo.core.utils.MoviedUtils
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityRegisterBinding
import com.edsusantoo.movied.ui.login.LoginActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()

    }

    private fun initView() {
        binding.appBar.tvTitle.text = getString(R.string.sign_up)
        binding.appBar.imgBack.setOnClickListener { finish() }

        initEditText()

        binding.togglePassword.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
        binding.toggleConfirmPassword.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.edtConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.edtConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }
        binding.btnGmail.setOnClickListener {
            Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initData() {
        binding.btnCreateAccount.setOnClickListener {
            MoviedUtils.hideKeyboard(this, binding.btnCreateAccount)
            registerViewModel.insertUser(
                username = binding.edtUsername.text.toString(),
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString(),
            ).observe(this, {
                if (it != null) {
                    when (it) {
                        is Resource.Success -> {
                            if (it.data.toString() != "-1") {
                                Toast.makeText(this, "Successfully add user", Toast.LENGTH_SHORT)
                                    .show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else if (it.data.toString() == "-1") {
                                Toast.makeText(
                                    this,
                                    "Failed to add user, user already taken",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                        is Resource.Error -> {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                        }
                    }
                }
            })
        }
    }

    @SuppressLint("CheckResult")
    private fun initEditText() {
        val usernameStream = RxTextView.textChanges(binding.edtUsername)
            .skipInitialValue()
            .map { username ->
                username.toString().isNotEmpty() && username.length < 3
            }
        usernameStream.subscribe {
            showUsernameNotEmpty(it)
        }

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
            showPasswordMinimalAlert(it)
        }

        val confirmPasswordStream = Observable.merge(
            RxTextView.textChanges(binding.edtPassword)
                .map { passwrod ->
                    passwrod.toString() != binding.edtConfirmPassword.text.toString()
                },
            RxTextView.textChanges(binding.edtConfirmPassword)
                .map { confirmPassword ->
                    confirmPassword.toString() != binding.edtPassword.text.toString()
                }
        )
        confirmPasswordStream.subscribe {
            showPasswordConfirmationAlert(it)
        }

        val invalidFieldStream = Observable.combineLatest(
            usernameStream,
            emailStream,
            passwordStream,
            confirmPasswordStream,
            { usernameInvalid: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean, confirmationPasswordInvalid: Boolean ->
                !usernameInvalid && !emailInvalid && !passwordInvalid && !confirmationPasswordInvalid
            })
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnCreateAccount.isEnabled = true
                binding.btnCreateAccount.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.green_500
                    )
                )
            } else {
                binding.btnCreateAccount.isEnabled = false
                binding.btnCreateAccount.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.grey_9C
                    )
                )
            }
        }
    }

    private fun showUsernameNotEmpty(isNotValid: Boolean) {
        binding.edtUsername.error = if (isNotValid) getString(R.string.username_not_valid) else null
    }

    private fun showEmailExistAlert(isNotValid: Boolean) {
        binding.edtEmail.error = if (isNotValid) getString(R.string.email_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.tvLabelErrorPassword.text =
            if (isNotValid) getString(R.string.password_not_valid) else ""
    }

    private fun showPasswordConfirmationAlert(isNotValid: Boolean) {
        binding.tvLabelErrorConfirmPassword.text =
            if (isNotValid) getString(R.string.password_not_same) else ""
    }
}