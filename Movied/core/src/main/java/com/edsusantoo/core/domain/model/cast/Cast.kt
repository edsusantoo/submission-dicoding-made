package com.edsusantoo.core.domain.model.cast

data class Cast(
    val idCast: String,
    val idMovie: String,
    val originalName: String,
    val profilePath: String? = null,
    val character: String
)
