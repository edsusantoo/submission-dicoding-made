package com.edsusantoo.movied.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.databinding.ItemSliderImageBinding
import com.smarteist.autoimageslider.SliderViewAdapter

class PopularSliderAdapter(
    private val list: List<Movie>?,
    private val listener: PopularSlideListener
) :
    SliderViewAdapter<PopularSliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view =
            ItemSliderImageBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularSliderAdapter.ViewHolder, position: Int) {
        holder.binding.let {
            Glide.with(it.root)
                .load(Constants.BASE_URL_IMAGE_MOVIE + list?.get(position)?.backdropPath)
                .fitCenter()
                .into(it.imgPoster)

            it.tvTitle.text = list?.get(position)?.originalTitle
            it.tvOverview.text = list?.get(position)?.overview
        }
        holder.itemView.setOnClickListener {
            list?.get(position)?.let { it1 -> listener.onClickListener(it1, position) }
        }
    }

    override fun getCount(): Int {
        return if (list != null)
            if (list.isNotEmpty())
                5
            else
                0
        else
            0
    }

    interface PopularSlideListener {
        fun onClickListener(data: Movie, position: Int)
    }

    inner class ViewHolder(val binding: ItemSliderImageBinding) :
        SliderViewAdapter.ViewHolder(binding.root)


}