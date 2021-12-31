package com.edsusantoo.movied.ui.upcoming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.edsusantoo.core.data.Resource
import com.edsusantoo.movied.databinding.FragmentUpComingBinding
import com.edsusantoo.movied.ui.adapter.MovieAnyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpComingFragment : Fragment() {
    private val upComingViewModel: UpComingViewModel by viewModels()

    private var _binding: FragmentUpComingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpComingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    private fun initView() {

    }

    private fun initData() {
        if (activity != null) {
            upComingViewModel.movieUpComing.observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.loading.isVisible = true
                        is Resource.Success -> {
                            binding.loading.isVisible = false
                            binding.rvUpComing.layoutManager =
                                GridLayoutManager(requireContext(), 2)
                            binding.rvUpComing.adapter = MovieAnyAdapter(movie.data)
                        }
                        is Resource.Error -> {
                            binding.loading.isVisible = false
                            Log.d("Movied", "Error")
                        }
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        fun getInstance() = UpComingFragment()
    }
}