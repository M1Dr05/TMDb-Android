package com.github.midros.tmdb.data.model

import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
class ObjectTv(
        var page: Int,
        var total_pages: Int,
        var results: MutableList<PojoResultsTv>
) : Serializable

data class PojoResultsTv(
        var genre_ids: MutableList<Int>,
        var name: String,
        var vote_count: Int,
        var first_air_date: String,
        var backdrop_path: String,
        var id: Int,
        var vote_average: Float,
        var overview: String,
        var poster_path: String
) : Serializable


data class PojoDetailsTv(
        var backdrop_path: String,
        var first_air_date: String,
        var genres: MutableList<ObjectListGenders>,
        var id: Int,
        var name: String,
        var number_of_episodes: Int,
        var number_of_seasons: Int,
        var overview: String,
        var vote_average: Float,
        var vote_count: Int,
        var status: String,
        var episode_run_time: MutableList<Int>,
        var homepage: String
) : Serializable

data class ObjectTvPerson(val cast:MutableList<PojoResultsTv>) : Serializable
