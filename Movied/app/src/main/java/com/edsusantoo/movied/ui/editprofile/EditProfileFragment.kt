package com.edsusantoo.movied.ui.editprofile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.FragmentEditProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@AndroidEntryPoint
class EditProfileFragment : BottomSheetDialogFragment() {
    private val editProfileViewModel: EditProfileViewModel by viewModels()

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()
    }

    private fun initView() {
        initEditText()
    }

    private fun initData() {
        editProfileViewModel.isLogin.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                binding.edtEmail.setText(user.email)
                binding.edtUsername.setText(user.username)
                binding.edtPassword.setText(user.password)
            }
        })
    }

    private fun editDataUser() {
        val username = binding.edtUsername.text.toString()
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        binding.btnUpdate.setOnClickListener {
            editProfileViewModel.isLogin.observe(viewLifecycleOwner, { user ->
                if (user != null) {
                    Log.d("ETSTSET", user.toString())
                    editProfileViewModel.updateUser(
                        User(
                            userId = user.userId,
                            username = username,
                            password = password,
                            email = email,
                            isLogin = user.isLogin
                        )
                    ).observe(viewLifecycleOwner, {
                        Log.d("UPDATEEE", it.toString())
                        if (it != -1) {
                            Toast.makeText(context, "Success update profile", Toast.LENGTH_SHORT)
                                .show()
                            dismiss()
                        }
                    })
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

        val invalidFieldStream = Observable.combineLatest(
            usernameStream,
            emailStream,
            passwordStream,
            { usernameInvalid: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean ->
                !usernameInvalid && !emailInvalid && !passwordInvalid
            }
        )
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnUpdate.isEnabled = true
                binding.btnUpdate.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green_500
                    )
                )
                editDataUser()
            } else {
                binding.btnUpdate.isEnabled = false
                binding.btnUpdate.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
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

    companion object {
        const val TAG = "EditProfileBottomSheetFragment"
    }
}
