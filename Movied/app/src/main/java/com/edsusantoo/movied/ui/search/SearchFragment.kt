package com.edsusantoo.movied.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.databinding.FragmentSearchBinding
import com.edsusantoo.movied.ui.adapter.MovieAnyAdapter
import com.edsusantoo.movied.ui.detailmovie.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (activity != null) {
            binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null && p0.isNotEmpty()) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            binding.groupSearchLabel.isVisible = false
                            binding.loading.isVisible = true
                            binding.rvSearch.isVisible = false
                            searchViewModel.searchMovie(p0)
                                .observe(viewLifecycleOwner, { movie ->
                                    if (movie != null && movie.data?.count() != 0) {
                                        when (movie) {
                                            is Resource.Loading -> {
                                                binding.groupSearchLabel.isVisible = false
                                                binding.loading.isVisible = true
                                                binding.rvSearch.isVisible = false
                                                binding.groupMovieEmpty.isVisible = false
                                            }
                                            is Resource.Success -> {
                                                binding.loading.isVisible = false
                                                binding.rvSearch.isVisible = true
                                                binding.groupMovieEmpty.isVisible = false
                                                binding.rvSearch.layoutManager =
                                                    GridLayoutManager(context, 2)
                                                binding.rvSearch.adapter =
                                                    MovieAnyAdapter(movie.data,
                                                        object : MovieAnyAdapter.MovieAnyListener {
                                                            override fun onClickListener(
                                                                data: Movie,
                                                                position: Int
                                                            ) {
                                                                val intent = Intent(
                                                                    activity,
                                                                    DetailMovieActivity::class.java
                                                                )
                                                                intent.putExtra(
                                                                    Constants.INTENT_DATA_PARCELABLE,
                                                                    movie.data?.get(position)
                                                                )
                                                                startActivity(intent)
                                                            }

                                                        })
                                            }
                                            is Resource.Error -> {
                                                Toast.makeText(
                                                    context,
                                                    movie.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } else {
                                        binding.loading.isVisible = false
                                        binding.rvSearch.isVisible = false
                                        binding.groupMovieEmpty.isVisible = true
                                    }
                                })
                        }, 800)

                    } else {
                        binding.groupSearchLabel.isVisible = true
                        binding.loading.isVisible = false
                        binding.rvSearch.isVisible = false
                        binding.groupMovieEmpty.isVisible = false
                    }
                    return true
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun getInstance() = SearchFragment()
    }

}