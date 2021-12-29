package com.edsusantoo.core.domain.model.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(

    val idMovie:String,

    val backdropPath:String,

    val budget:String?=null,

    val genres:String?=null,

    val originalTitle:String,

    val overview:String,

    val posterPath:String,

    val productionCompanies:String?=null,

    val productionCountries:String?=null,

    val releaseDate:String,

    val runtime:String?=null,

    val tagline:String?=null,

    val voteAverage:String,

    val isFavorite:Boolean = false,

    //type movie : popular, up_coming, coming_soon
    val typeMovie:String

): Parcelable