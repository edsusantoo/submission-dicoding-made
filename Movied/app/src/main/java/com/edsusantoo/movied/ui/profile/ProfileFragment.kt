package com.edsusantoo.movied.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.movied.databinding.FragmentProfileBinding
import com.edsusantoo.movied.ui.editprofile.EditProfileFragment
import com.edsusantoo.movied.ui.welcomescreen.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.btnEditProfile.setOnClickListener {
            val editProfile = EditProfileFragment()
            editProfile.show(childFragmentManager, EditProfileFragment.TAG)

        }

        binding.btnLogout.setOnClickListener {
            profileViewModel.isLogin.observe(viewLifecycleOwner, { user ->
                if (user != null) {
                    profileViewModel.updateUser(
                        User(
                            userId = user.userId,
                            username = user.username,
                            password = user.password,
                            email = user.email,
                            isLogin = false
                        )
                    ).observe(viewLifecycleOwner, {
                        if (it != -1) {
                            startActivity(Intent(activity, WelcomeActivity::class.java))
                            activity?.finish()
                        }
                    })
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance() = ProfileFragment()
    }
}