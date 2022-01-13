package com.edsusantoo.core.utils

import com.edsusantoo.core.data.source.local.entity.CastEntity
import com.edsusantoo.core.data.source.local.entity.FavoriteEntity
import com.edsusantoo.core.data.source.local.entity.MovieEntity
import com.edsusantoo.core.data.source.local.entity.UserEntity
import com.edsusantoo.core.data.source.local.entity.join.MovieFavorite
import com.edsusantoo.core.data.source.remote.response.movie.cast.CastResponse
import com.edsusantoo.core.data.source.remote.response.movie.list.ListMovieResponse
import com.edsusantoo.core.domain.model.cast.Cast
import com.edsusantoo.core.domain.model.favorite.Favorite
import com.edsusantoo.core.domain.model.movie.Movie
import com.edsusantoo.core.domain.model.user.User
import com.edsusantoo.core.domain.model.moviefavorite.MovieFavorite as MovieFavoriteDomain

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
                tagline = "",
            )
        }

    fun mapMovieResponseToEntities(detail: Movie, typeMovie: String): MovieEntity =
        MovieEntity(
            idMovie = detail.idMovie,
            backdropPath = detail.backdropPath,
            originalTitle = detail.originalTitle,
            overview = detail.overview,
            posterPath = detail.posterPath,
            releaseDate = detail.releaseDate,
            voteAverage = detail.voteAverage.toString(),
            typeMovie = typeMovie,
            productionCompanies = detail.productionCompanies,
            budget = detail.budget.toString(),
            genres = detail.genres,
            productionCountries = detail.productionCountries,
            runtime = detail.runtime.toString(),
            tagline = detail.tagline,
            video = detail.video,
            typeVideo = detail.typeVideo
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
            tagline = movieEntity.tagline,
            typeVideo = movieEntity.typeVideo,
            video = movieEntity.video
        )

    fun mapFavoriteDomainToEntities(favorite: Favorite): FavoriteEntity =
        FavoriteEntity(
            idFavorite = favorite.idFavorite,
            isFavorite = favorite.isFavorite
        )

    fun mapMovieFavoriteEntitiesToDomain(movieFavorite: MovieFavorite) =
        MovieFavoriteDomain(
            idMovie = movieFavorite.idMovie,
            backdropPath = movieFavorite.backdropPath,
            originalTitle = movieFavorite.originalTitle,
            overview = movieFavorite.overview,
            posterPath = movieFavorite.posterPath,
            releaseDate = movieFavorite.releaseDate,
            voteAverage = movieFavorite.voteAverage,
            typeMovie = movieFavorite.typeMovie,
            productionCompanies = movieFavorite.productionCompanies,
            budget = movieFavorite.budget,
            genres = movieFavorite.genres,
            isFavorite = movieFavorite.isFavorite,
            productionCountries = movieFavorite.productionCountries,
            runtime = movieFavorite.runtime,
            tagline = movieFavorite.tagline,
            video = movieFavorite.video,
            typeVideo = movieFavorite.typeVideo
        )

    fun mapListMovieFavoriteEntitiesToDomain(movieFavorite: List<MovieFavorite>) =
        movieFavorite.map {
            MovieFavoriteDomain(
                idMovie = it.idMovie,
                backdropPath = it.backdropPath,
                originalTitle = it.originalTitle,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                typeMovie = it.typeMovie,
                productionCompanies = it.productionCompanies,
                budget = it.budget,
                genres = it.genres,
                isFavorite = it.isFavorite,
                productionCountries = it.productionCountries,
                runtime = it.runtime,
                tagline = it.tagline,
                video = it.video,
                typeVideo = it.typeVideo
            )
        }

    fun mapUserEntitiesToDomain(user: UserEntity): User =
        User(
            userId = user.userId,
            username = user.username,
            email = user.email,
            password = user.password,
            isLogin = user.isLogin
        )

    fun mapUserDomainToEntities(user: User): UserEntity =
        UserEntity(
            userId = user.userId,
            username = user.username,
            email = user.email,
            password = user.password,
            isLogin = user.isLogin
        )

    fun mapListUserEntitiesToDomain(user: List<UserEntity>): List<User> =
        user.map {
            User(
                userId = it.userId,
                username = it.username,
                email = it.email,
                password = it.password,
                isLogin = it.isLogin
            )
        }
}