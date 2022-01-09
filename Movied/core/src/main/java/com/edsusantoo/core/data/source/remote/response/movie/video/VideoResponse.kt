package com.edsusantoo.core.data.source.remote.response.movie.video


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)