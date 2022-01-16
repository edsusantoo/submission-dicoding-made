package com.edsusantoo.core.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    val idMovie: String,

    val backdropPath: String? = null,

    val budget: String? = null,

    val genres: String? = null,

    val originalTitle: String? = null,

    val overview: String? = null,

    val posterPath: String? = null,

    val productionCompanies: String? = null,

    val productionCountries: String? = null,

    val releaseDate: String? = null,

    val runtime: String? = null,

    val tagline: String? = null,

    val voteAverage: String? = null,

    val isFavorite: Boolean? = null,

    val video: String? = null,

    val typeVideo: String? = null,

    // type movie : popular, up_coming, coming_soon
    val typeMovie: String? = null

) : Parcelable
