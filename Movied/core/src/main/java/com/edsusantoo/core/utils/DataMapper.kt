package com.edsusantoo.core.utils

import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.remote.response.movie.detail.DetailMovieResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
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
                idMovie = it.idMovie.toString(),
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
                isFavorite = it.isFavorite,
                productionCountries = it.productionCountries,
                runtime = it.runtime,
                tagline = it.tagline
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
            isFavorite = domain.isFavorite,
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
            isFavorite = movieEntity.isFavorite,
            productionCountries = movieEntity.productionCountries,
            runtime = movieEntity.runtime,
            tagline = movieEntity.tagline
        )
}