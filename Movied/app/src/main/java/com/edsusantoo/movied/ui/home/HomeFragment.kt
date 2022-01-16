package com.edsusantoo.movied.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.FragmentHomeBinding
import com.edsusantoo.movied.ui.detailmovie.DetailMovieActivity
import com.edsusantoo.movied.ui.home.adapter.MovieViewPagerAdapter
import com.edsusantoo.movied.ui.home.adapter.PopularSliderAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initView() {
        if (activity != null) {
            val tabMovie = binding.tabMovie
            val vpMovie = binding.vpMovie
            val adapter = MovieViewPagerAdapter(childFragmentManager, lifecycle)
            vpMovie.adapter = adapter
            TabLayoutMediator(tabMovie, vpMovie) { tab, position ->
                when (position) {
                    0 -> tab.text = "Now Showing"
                    1 -> tab.text = "Coming Soon"
                }
            }.attach()
        }
    }

    private fun initData() {
        if (activity != null) {
            homeViewModel.moviePopular.observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.loadingSlider.isVisible = true
                        is Resource.Success -> {
                            binding.loadingSlider.isVisible = false
                            binding.imageSlider.setSliderAdapter(
                                PopularSliderAdapter(
                                    movie.data,
                                    object : PopularSliderAdapter.PopularSlideListener {
                                        override fun onClickListener(data: Movie, position: Int) {
                                            val intent =
                                                Intent(activity, DetailMovieActivity::class.java)
                                            intent.putExtra(
                                                Constants.INTENT_DATA_PARCELABLE,
                                                movie.data?.get(position)
                                            )
                                            startActivity(intent)
                                        }
                                    }
                                )
                            )
                            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM)
                        }
                        is Resource.Error -> {
                            binding.loadingSlider.isVisible = false
                            Log.d("Movied Error", "Error")
                        }
                    }
                }
            })

            homeViewModel.isLogin.observe(viewLifecycleOwner, { user ->
                if (user != null) {
                    binding.tvHelloUser.text = getString(R.string.hello_variable, user.username)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance() = HomeFragment()
    }
}
