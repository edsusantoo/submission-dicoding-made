package com.edsusantoo.movied.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.edsusantoo.movied.ui.nowshowing.NowShowingFragment
import com.edsusantoo.movied.ui.upcoming.UpComingFragment

class MovieViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowShowingFragment.getInstance()
            else -> UpComingFragment.getInstance()
        }
    }
}
