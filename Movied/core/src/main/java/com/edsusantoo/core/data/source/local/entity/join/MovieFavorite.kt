package com.edsusantoo.core.data.source.local.entity.join

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieFavorite(

    @ColumnInfo(name = "id_movie")
    val idMovie: String? = null,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,

    val budget: String? = null,

    val genres: String? = null,

    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    val overview: String? = null,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "production_companies")
    val productionCompanies: String? = null,

    @ColumnInfo(name = "production_countries")
    val productionCountries: String? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    val runtime: String? = null,

    val tagline: String? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: String? = null,

    @ColumnInfo(name = "id_favorite")
    val idFavorite: String? = null,

    @ColumnInfo(name = "favorite")
    val isFavorite: Boolean? = null,

    @ColumnInfo(name = "video")
    val video: String? = null,

    @ColumnInfo(name = "type_video")
    val typeVideo: String? = null,

    // type movie : popular, up_coming, coming_soon
    @ColumnInfo(name = "type_movie")
    val typeMovie: String? = null
) : Parcelable
