package com.edsusantoo.core.data.source.remote.response.movie.cast


import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)