package com.edsusantoo.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cast")
data class CastEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_cast")
    var idCast: String,

    @NonNull
    @ColumnInfo(name = "id_movie")
    var idMovie: String,

    @ColumnInfo(name = "original_name")
    var originalName: String,

    @ColumnInfo(name = "character")
    var character: String,

    @ColumnInfo(name = "porifle_path")
    var profilePath: String?
) : Parcelable
