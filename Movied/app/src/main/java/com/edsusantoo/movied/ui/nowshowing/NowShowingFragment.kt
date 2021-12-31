package com.edsusantoo.movied.ui.nowshowing

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
import com.edsusantoo.movied.databinding.FragmentNowShowingBinding
import com.edsusantoo.movied.ui.adapter.MovieAnyAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowShowingFragment : Fragment() {

    private val nowShowingViewModel: NowShowingViewModel by viewModels()

    private var _binding: FragmentNowShowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNowShowingBinding.inflate(inflater, container, false)
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
            nowShowingViewModel.movieNowShowing.observe(viewLifecycleOwner, { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> binding.loading.isVisible = true
                        is Resource.Success -> {
                            binding.loading.isVisible = false
                            binding.rvNowShowing.layoutManager =
                                GridLayoutManager(requireContext(), 2)
                            binding.rvNowShowing.adapter = MovieAnyAdapter(movie.data)
                        }
                        is Resource.Error -> {
                            binding.loading.isVisible = false
                            Log.d("Movied Error", "Error")
                        }
                    }
                }
            })
        }
    }

    companion object {
        fun getInstance() = NowShowingFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}