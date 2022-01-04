package com.edsusantoo.core.utils

import com.edsusantoo.core.data.source.local.entity.CastEntity
import com.edsusantoo.core.data.source.local.entity.FavoriteEntity
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie

object DataMapper {
    fun mapListMovieResponseToEntities(response:ListMovieResponse,type:String):List<MovieEntity>{
        val movieEntityArray = ArrayList<MovieEntity>()
        response.results.map {
            val movie = MovieEntity(
                idMovie = it.id.toString(),
                backdropPath = it.backdropPath,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage.toString(),
                typeMovie = type
            )
            movieEntityArray.add(movie)
        }
        return movieEntityArray
    }

    fun mapListMovieEntitiesToDomain(movieEntity: List<MovieEntity>):List<Movie> =
        movieEntity.map {
            Movie(
                idMovie = it.idMovie,
                backdropPath = it.backdropPath,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage.toString(),
                typeMovie = it.typeMovie,
                productionCompanies = it.productionCompanies,
                budget = it.budget,
                genres = it.genres,
                productionCountries = it.productionCountries,
                runtime = it.runtime,
                tagline = it.tagline
            )
        }

    fun mapListMovieResponseToDomain(response:ListMovieResponse):List<Movie> =
        response.results.map {
            Movie(
                idMovie = it.id.toString(),
                backdropPath = it.backdropPath,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage.toString(),
                typeMovie = "",
                productionCompanies = "",
                budget = "",
                genres = "",
                isFavorite = false,
                productionCountries = "",
                runtime = "",
                tagline = ""
            )
        }

    fun mapMovieResponseToEntities(detail:DetailMovieResponse,typeMovie:String):MovieEntity =
        MovieEntity(
            idMovie = detail.id.toString(),
            backdropPath = detail.backdropPath,
            originalTitle = detail.originalTitle,
            overview = detail.overview,
            posterPath = detail.posterPath,
            releaseDate = detail.releaseDate,
            voteAverage = detail.voteAverage.toString(),
            typeMovie = typeMovie,
            productionCompanies = MoviedUtils.convertToStringJson(detail.productionCompanies),
            budget = detail.budget.toString(),
            genres = MoviedUtils.convertToStringJson(detail.genres),
            productionCountries = MoviedUtils.convertToStringJson(detail.productionCountries),
            runtime = detail.runtime.toString(),
            tagline = detail.tagline
        )

    fun mapCastResponseToEntities(response:CastResponse):List<CastEntity> {
        val castEntityArray = ArrayList<CastEntity>()
        response.cast.map {
            val cast = CastEntity(
                idCast = it.id.toString(),
                idMovie = response.id.toString(),
                originalName = it.originalName,
                character = it.character,
                profilePath = it.profilePath
            )
            castEntityArray.add(cast)
        }
        return castEntityArray
    }

    fun mapListCastEntitiesToDomain(castEntity: List<CastEntity>):List<Cast> =
        castEntity.map {
            Cast(
                idCast = it.idCast,
                idMovie = it.idMovie,
                originalName = it.originalName,
                character = it.character,
                profilePath = it.profilePath
            )
        }


    fun mapMovieDomainToEntities(domain:Movie):MovieEntity =
        MovieEntity(
            idMovie = domain.idMovie,
            backdropPath = domain.backdropPath,
            originalTitle = domain.originalTitle,
            overview = domain.overview,
            posterPath = domain.posterPath,
            releaseDate = domain.releaseDate,
            voteAverage = domain.voteAverage,
            typeMovie = domain.typeMovie,
            productionCompanies = domain.productionCompanies,
            budget = domain.budget,
            genres = domain.genres,
            productionCountries = domain.productionCountries,
            runtime = domain.runtime,
            tagline = domain.tagline
        )

    fun mapMovieEntitiesToDomain(movieEntity: MovieEntity):Movie=
        Movie(
            idMovie = movieEntity.idMovie,
            backdropPath = movieEntity.backdropPath,
            originalTitle = movieEntity.originalTitle,
            overview = movieEntity.overview,
            posterPath = movieEntity.posterPath,
            releaseDate = movieEntity.releaseDate,
            voteAverage = movieEntity.voteAverage,
            typeMovie = movieEntity.typeMovie,
            productionCompanies = movieEntity.productionCompanies,
            budget = movieEntity.budget,
            genres = movieEntity.genres,
            productionCountries = movieEntity.productionCountries,
            runtime = movieEntity.runtime,
            tagline = movieEntity.tagline
        )

    fun mapFavoriteMovieDomainToEntities(favorite: Favorite):FavoriteEntity =
        FavoriteEntity(
            idFavorite = favorite.idFavorite,
            isFavorite = favorite.isFavorite
        )

}