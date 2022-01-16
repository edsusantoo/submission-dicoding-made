package com.edsusantoo.movied.ui.detailmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.utils.Constants
import com.edsusantoo.movied.R
import com.edsusantoo.movied.databinding.ItemCastBinding

class CastAdapter(private val list: List<Cast>?) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastAdapter.ViewHolder {
        val view = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastAdapter.ViewHolder, position: Int) {
        holder.binding.let {

            Glide.with(it.root)
                .load(Constants.BASE_URL_IMAGE_MOVIE_W92 + list?.get(position)?.profilePath)
                .error(R.drawable.profile_template)
                .fitCenter()
                .into(it.imgCast)

            it.tvRealName.text = list?.get(position)?.originalName
            it.tvNameMovie.text = list?.get(position)?.character
        }
    }

    override fun getItemCount(): Int {
        return if (list != null)
            if (list.isNotEmpty())
                if (list.count() >= 5)
                    5
                else
                    list.size
            else
                0
        else
            0
    }

    inner class ViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)
}
