package com.edsusantoo.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_movie")
    var idMovie:String,

    @ColumnInfo(name = "backdrop_path")
    var backdropPath:String?=null,

    @ColumnInfo(name = "budget")
    var budget:String?=null,

    @ColumnInfo(name = "genres")
    var genres:String?=null,

    @ColumnInfo(name = "original_title")
    var originalTitle:String,

    @ColumnInfo(name = "overview")
    var overview:String,

    @ColumnInfo(name = "poster_path")
    var posterPath:String,

    @ColumnInfo(name = "production_companies")
    var productionCompanies:String?=null,

    @ColumnInfo(name = "production_countries")
    var productionCountries:String?=null,

    @ColumnInfo(name = "release_date")
    var releaseDate:String,

    @ColumnInfo(name = "runtime")
    var runtime:String?=null,

    @ColumnInfo(name = "tagline")
    var tagline:String?=null,

    @ColumnInfo(name = "vote_average")
    var voteAverage:String,

    //type movie : popular, up_coming, coming_soon
    @ColumnInfo(name = "type_movie")
    var typeMovie:String

):Parcelable
