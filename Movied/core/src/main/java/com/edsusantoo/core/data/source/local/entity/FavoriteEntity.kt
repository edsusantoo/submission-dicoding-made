package com.edsusantoo.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_favorite")
    var idFavorite:String,

    @ColumnInfo(name = "favorite")
    var isFavorite:Boolean,
):Parcelable