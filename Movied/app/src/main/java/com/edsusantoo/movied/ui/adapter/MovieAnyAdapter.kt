package com.edsusantoo.movied.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ItemMovie1Binding

class MovieAnyAdapter(private val list: List<Movie>?,private val movieAnyListener: MovieAnyListener) :
    RecyclerView.Adapter<MovieAnyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAnyAdapter.ViewHolder {
        val view = ItemMovie1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieAnyAdapter.ViewHolder, position: Int) {
        holder.binding.let {
            Glide.with(it.root)
                .load(Constants.BASE_URL_IMAGE_MOVIE + list?.get(position)?.posterPath)
                .error(R.drawable.movie_poster_template)
                .fitCenter()
                .into(it.imgPoster)

            it.tvTitle.text = list?.get(position)?.originalTitle
            it.tvRate.text = it.tvRate.context.getString(
                R.string.rate_variable,
                list?.get(position)?.voteAverage
            )
            if (list?.get(position)?.voteAverage != null) {
                it.circularRate.progress = (list[position].voteAverage!!.toDouble() * 10.0).toInt()
            } else {
                it.circularRate.progress = 0
            }

        }

        holder.itemView.setOnClickListener {
            list?.get(position)?.let { it1 -> movieAnyListener.onClickListener(it1,position) }
        }
    }

    override fun getItemCount(): Int {
        return if (list != null)
            if (list.isNotEmpty())
            //to fix lagging recyclerview
                if (list.size >= 15)
                    15
                else
                    list.size
            else
                0
        else
            0
    }

    interface MovieAnyListener {
        fun onClickListener(data : Movie,position: Int)
    }

    inner class ViewHolder(val binding: ItemMovie1Binding) : RecyclerView.ViewHolder(binding.root)
}