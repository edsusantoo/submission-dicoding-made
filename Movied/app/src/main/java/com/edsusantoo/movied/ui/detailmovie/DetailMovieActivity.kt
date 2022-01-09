package com.edsusantoo.movied.ui.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.edsusantoo.core.data.Resource
import com.edsusantoo.core.data.source.remote.response.movie.detail.Genre
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.core.utils.MoviedUtils
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ActivityDetailMovieBinding
import com.edsusantoo.movied.ui.detailmovie.adapter.CastAdapter
import com.edsusantoo.movied.ui.detailmovie.adapter.RelatedMovieAdapter
import com.google.android.material.chip.Chip
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {
    private val detailMovieViewModel:DetailMovieViewModel by viewModels()


    private lateinit var binding:ActivityDetailMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val type = intent.getStringExtra(Constants.INTENT_DATA_TYPE)
        if (type != null && type == Constants.TYPE_DETAIL_FAVORITE_MOVIE) {
            val data = intent.getParcelableExtra<MovieFavorite>(Constants.INTENT_DATA_PARCELABLE)
            if (data != null) {
                data.typeMovie?.let {
                    detailMovieViewModel.detailMovie(data.idMovie!!, it).observe(this, { movie ->
                        if (movie != null) {
                            when (movie) {
                                is Resource.Loading -> Log.d("Loading", "Loading")
                                is Resource.Success -> {
                                    Glide.with(binding.root)
                                        .load(Constants.BASE_URL_IMAGE_MOVIE + movie.data?.backdropPath)
                                        .error(R.drawable.movie_backdrop_path_template)
                                        .fitCenter()
                                        .into(binding.imgBackdropPath)


                                    Glide.with(binding.root)
                                        .load(Constants.BASE_URL_IMAGE_MOVIE + movie.data?.posterPath)
                                        .error(R.drawable.movie_poster_template)
                                        .fitCenter()
                                        .into(binding.imgPosterPath)



                                    binding.tvTitle.text = movie.data?.originalTitle
                                    binding.tvDuration.text = movie.data?.runtime + " " + "minutes"
                                    binding.tvRate.text = movie.data?.voteAverage.toString()
                                    binding.tvReleaseDate.text = MoviedUtils.convertDate(
                                        movie.data?.releaseDate,
                                        "MMMM dd, yyyy"
                                    )
                                    val listGenre: List<Genre> = GsonBuilder().create()
                                        .fromJson(movie.data?.genres, Array<Genre>::class.java)
                                        .toList()
                                    listGenre.map { genre ->
                                        val chipGenre = Chip(this)
                                        chipGenre.text = genre.name
                                        chipGenre.chipBackgroundColor = ColorStateList.valueOf(
                                            ContextCompat.getColor(this, R.color.grey_2B)
                                        )
                                        binding.chipGenre.addView(chipGenre)
                                    }
                                    binding.tvOverview.text = movie.data?.overview

                                    binding.btnPlay.setOnClickListener {
                                        val video = movie.data?.video
                                        val site = movie.data?.typeMovie
                                        val uri =
                                            Uri.parse("movied://videoplayer?video=${video}&site=${site}".trim())
                                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    }
                                }
                                is Resource.Error -> {
                                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }

                detailMovieViewModel.castMovie(data.idMovie!!).observe(this, { cast ->
                    if (cast != null) {
                        if (cast.data?.count() != 0) {

                            when (cast) {
                                is Resource.Loading -> Log.d("Loading", "Loading")
                                is Resource.Success -> {
                                    binding.rvCast.layoutManager = GridLayoutManager(this, 2)
                                    binding.rvCast.adapter = CastAdapter(cast.data)
                                }
                                is Resource.Error -> {
                                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            binding.tvLabelCast.isVisible = false
                            binding.rvCast.isVisible = false
                        }
                    }
                })

                detailMovieViewModel.relatedMovie(data.idMovie!!).observe(this, { movie ->
                    if (movie != null) {
                        binding.tvLabelRelatedMovie.isVisible = true
                        binding.rvRelatedMove.isVisible = true
                        when (movie) {
                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {
                                if (movie.data.isNullOrEmpty()) {
                                    binding.tvLabelRelatedMovie.isVisible = false
                                    binding.rvRelatedMove.isVisible = false
                                } else {
                                    binding.rvRelatedMove.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                        )
                                    binding.rvRelatedMove.adapter = RelatedMovieAdapter(movie.data)
                                }
                            }
                            is Resource.Error -> {
                            }
                        }
                    } else {
                        binding.tvLabelRelatedMovie.isVisible = false
                        binding.rvRelatedMove.isVisible = false
                    }
                })

                detailMovieViewModel.getLocalFavorite(data.idMovie!!).observe(this, { movie ->
                    movie?.let {
                        binding.toggleFavorite.isChecked = it.isFavorite == true
                    }
                })

            }
        } else {
            val data = intent.getParcelableExtra<Movie>(Constants.INTENT_DATA_PARCELABLE)
            if (data != null) {
                data.typeMovie?.let {
                    detailMovieViewModel.detailMovie(data.idMovie, it).observe(this, { movie ->
                        if (movie != null) {
                            when (movie) {
                                is Resource.Loading -> Log.d("Loading", "Loading")
                                is Resource.Success -> {
                                    Glide.with(binding.root)
                                        .load(Constants.BASE_URL_IMAGE_MOVIE + movie.data?.backdropPath)
                                        .error(R.drawable.movie_backdrop_path_template)
                                        .fitCenter()
                                        .into(binding.imgBackdropPath)


                                    Glide.with(binding.root)
                                        .load(Constants.BASE_URL_IMAGE_MOVIE + movie.data?.posterPath)
                                        .error(R.drawable.movie_poster_template)
                                        .fitCenter()
                                        .into(binding.imgPosterPath)



                                    binding.tvTitle.text = movie.data?.originalTitle
                                    binding.tvDuration.text = movie.data?.runtime + " " + "minutes"
                                    binding.tvRate.text = movie.data?.voteAverage.toString()
                                    binding.tvReleaseDate.text = MoviedUtils.convertDate(
                                        movie.data?.releaseDate,
                                        "MMMM dd, yyyy"
                                    )

                                    if (movie.data?.genres != null) {
                                        val listGenre: List<Genre> = GsonBuilder().create()
                                            .fromJson(movie.data?.genres, Array<Genre>::class.java)
                                            .toList()
                                        listGenre.map { genre ->
                                            val chipGenre = Chip(this)
                                            chipGenre.text = genre.name
                                            binding.chipGenre.addView(chipGenre)
                                        }
                                    }
                                    binding.tvOverview.text = movie.data?.overview

                                    binding.btnPlay.setOnClickListener {
                                        val video = movie.data?.video
                                        val site = movie.data?.typeVideo

                                        val uri =
                                            Uri.parse("movied://videoplayer?video=${video}&site=${site}".trim())
                                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    }
                                }
                                is Resource.Error -> {
                                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }

                detailMovieViewModel.castMovie(data.idMovie).observe(this, { cast ->
                    if (cast != null) {
                        if (cast.data?.count() != 0) {

                            when (cast) {
                                is Resource.Loading -> Log.d("Loading", "Loading")
                                is Resource.Success -> {
                                    binding.rvCast.layoutManager = GridLayoutManager(this, 2)
                                    binding.rvCast.adapter = CastAdapter(cast.data)
                                }
                                is Resource.Error -> {
                                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            binding.tvLabelCast.isVisible = false
                            binding.rvCast.isVisible = false
                        }
                    }
                })

                detailMovieViewModel.relatedMovie(data.idMovie).observe(this, { movie ->
                    if (movie != null) {
                        binding.tvLabelRelatedMovie.isVisible = true
                        binding.rvRelatedMove.isVisible = true
                        when (movie) {
                            is Resource.Loading -> {
                            }
                            is Resource.Success -> {
                                if (movie.data.isNullOrEmpty()) {
                                    binding.tvLabelRelatedMovie.isVisible = false
                                    binding.rvRelatedMove.isVisible = false
                                } else {
                                    binding.rvRelatedMove.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.HORIZONTAL,
                                            false
                                        )
                                    binding.rvRelatedMove.adapter = RelatedMovieAdapter(movie.data)
                                }
                            }
                            is Resource.Error -> {
                            }
                        }
                    } else {
                        binding.tvLabelRelatedMovie.isVisible = false
                        binding.rvRelatedMove.isVisible = false
                    }
                })

                detailMovieViewModel.getLocalFavorite(data.idMovie).observe(this, { movie ->
                    movie?.let {
                        if (it.isFavorite != null) {
                            binding.toggleFavorite.isChecked = it.isFavorite!!
                        }
                    }
                })


            }
        }

    }

    private fun initView() {
        binding.imgBack.setOnClickListener { finish() }
        val type = intent.getStringExtra(Constants.INTENT_DATA_TYPE)
        if (type != null && type == Constants.TYPE_DETAIL_FAVORITE_MOVIE) {
            val data = intent.getParcelableExtra<MovieFavorite>(Constants.INTENT_DATA_PARCELABLE)
            if (data != null) {
                binding.toggleFavorite.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        detailMovieViewModel.setFavorite(Favorite(data.idMovie!!, b))
                        Toast.makeText(
                            this,
                            "Successfully added favorite movie",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        detailMovieViewModel.setFavorite(Favorite(data.idMovie!!, b))
                        Toast.makeText(
                            this,
                            "Successfully deleted favorite movie",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            val data = intent.getParcelableExtra<Movie>(Constants.INTENT_DATA_PARCELABLE)
            if (data != null) {
                binding.toggleFavorite.setOnCheckedChangeListener { _, b ->
                    if (b) {
                        detailMovieViewModel.setFavorite(Favorite(data.idMovie, b))
                        Toast.makeText(
                            this,
                            "Successfully added favorite movie",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        detailMovieViewModel.setFavorite(Favorite(data.idMovie, b))
                        Toast.makeText(
                            this,
                            "Successfully deleted favorite movie",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}