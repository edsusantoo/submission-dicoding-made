package com.edsusantoo.movied.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityMainBinding
import com.edsusantoo.movied.ui.favorite.FavoriteFragment
import com.edsusantoo.movied.ui.home.HomeFragment
import com.edsusantoo.movied.ui.login.LoginActivity
import com.edsusantoo.movied.ui.profile.ProfileFragment
import com.edsusantoo.movied.ui.search.SearchFragment
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        setCurrentFragment(HomeFragment.getInstance())
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> setCurrentFragment(HomeFragment.getInstance())
                R.id.nav_search -> setCurrentFragment(SearchFragment.getInstance())
                R.id.nav_favorite -> setCurrentFragment(FavoriteFragment.getInstance())
                R.id.nav_profile -> setCurrentFragment(ProfileFragment.getInstance())
                else -> setCurrentFragment(HomeFragment.getInstance())
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNav.setOnItemReselectedListener {  }
    }

    private fun setCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container,fragment)
            commit()
        }
    }
}