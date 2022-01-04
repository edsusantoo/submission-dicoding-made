package com.edsusantoo.movied.ui.favorite.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ItemFavoriteMovieBinding

class FavoriteMovieAdapter(
    private val list: List<MovieFavorite>?,
    private val favoriteMovieListener: FavoriteMovieListener
) : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapter.ViewHolder {
        val view =
            ItemFavoriteMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteMovieAdapter.ViewHolder, position: Int) {
        holder.binding.let {
            Glide.with(it.root)
                .load(Constants.BASE_URL_IMAGE_MOVIE + list?.get(position)?.posterPath)
                .error(R.drawable.movie_poster_template)
                .fitCenter()
                .into(it.imgPoster)

            it.tvTitle.text = list?.get(position)?.originalTitle
            it.tvDuration.text = list?.get(position)?.runtime + " minutes"
            it.tvRate.text = list?.get(position)?.voteAverage

            favoriteMovieListener.onViewHolder(holder.binding, list?.get(position), position)
            holder.itemView.setOnClickListener {
                list?.get(position)
                    ?.let { it1 -> favoriteMovieListener.onClickListener(it1, position) }
            }
        }
    }

    interface FavoriteMovieListener {
        fun onViewHolder(view: ItemFavoriteMovieBinding, data: MovieFavorite?, position: Int)
        fun onClickListener(data: MovieFavorite?, position: Int)
    }

    override fun getItemCount(): Int {
        return if (list != null)
            if (list.isNotEmpty())
                list.size
            else
                0
        else
            0
    }

    inner class ViewHolder(val binding: ItemFavoriteMovieBinding) :
        RecyclerView.ViewHolder(binding.root)
}