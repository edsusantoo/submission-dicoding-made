package com.edsusantoo.movied.ui.favorite

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edsusantoo.core.data.source.remote.response.movie.detail.Genre
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.FragmentFavoriteBinding
import com.edsusantoo.movied.databinding.ItemFavoriteMovieBinding
import com.edsusantoo.movied.ui.detailmovie.DetailMovieActivity
import com.edsusantoo.movied.ui.favorite.adapter.FavoriteMovieAdapter
import com.google.android.material.chip.Chip
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
            favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner, { movieFavorite ->
                binding.loading.isVisible = true
                if (movieFavorite != null) {
                    if (movieFavorite.isNotEmpty()) {
                        binding.loading.isVisible = false
                        binding.rvFavorite.isVisible = true
                        binding.groupFavorite.isVisible = false

                        binding.rvFavorite.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.rvFavorite.adapter = FavoriteMovieAdapter(movieFavorite,
                            object : FavoriteMovieAdapter.FavoriteMovieListener {
                                override fun onViewHolder(
                                    view: ItemFavoriteMovieBinding,
                                    data: MovieFavorite?,
                                    position: Int
                                ) {
                                    if (data?.genres != null) {
                                        val listGenre: List<Genre> = GsonBuilder().create()
                                            .fromJson(data.genres, Array<Genre>::class.java)
                                            .toList()
                                        listGenre.map { genre ->
                                            val chipGenre = Chip(requireContext())
                                            chipGenre.text = genre.name
                                            chipGenre.textSize = 12.0F
                                            chipGenre.chipBackgroundColor = ColorStateList.valueOf(
                                                ContextCompat.getColor(
                                                    requireContext(),
                                                    R.color.grey_2B
                                                )
                                            )
                                            view.chipGenre.addView(chipGenre)
                                        }
                                    }

                                }

                                override fun onClickListener(
                                    data: MovieFavorite?,
                                    position: Int
                                ) {
                                    val intent = Intent(activity, DetailMovieActivity::class.java)
                                    intent.putExtra(Constants.INTENT_DATA_PARCELABLE, data)
                                    intent.putExtra(
                                        Constants.INTENT_DATA_TYPE,
                                        Constants.TYPE_DETAIL_FAVORITE_MOVIE
                                    )
                                    startActivity(intent)
                                }

                            })
                    } else {
                        binding.loading.isVisible = false
                        binding.rvFavorite.isVisible = false
                        binding.groupFavorite.isVisible = true
                    }
                } else {
                    binding.loading.isVisible = false
                    binding.rvFavorite.isVisible = false
                    binding.groupFavorite.isVisible = true
                }
            })
        }
    }

    companion object {
        fun getInstance() = FavoriteFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}