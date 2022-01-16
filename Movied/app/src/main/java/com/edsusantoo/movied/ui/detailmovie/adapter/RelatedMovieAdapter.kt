package com.edsusantoo.movied.ui.detailmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ItemMovie2Binding

class RelatedMovieAdapter(private val list: List<Movie>?) :
    RecyclerView.Adapter<RelatedMovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedMovieAdapter.ViewHolder {
        val view = ItemMovie2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RelatedMovieAdapter.ViewHolder, position: Int) {
        holder.binding.let {

            Glide.with(it.root)
                .load(Constants.BASE_URL_IMAGE_MOVIE + list?.get(position)?.backdropPath)
                .error(R.drawable.movie_backdrop_path_template)
                .fitCenter()
                .into(it.imgRelatedMovie)

            val date = list?.get(position)?.releaseDate?.split("-")
            it.tvTitle.text = it.tvTitle.context.getString(
                R.string.title_variable_relate_movie,
                list?.get(position)?.originalTitle,
                date?.get(0)
            )
        }
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

    inner class ViewHolder(val binding: ItemMovie2Binding) : RecyclerView.ViewHolder(binding.root)
}
