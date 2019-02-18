package com.github.midros.tmdb.data.api

import com.github.midros.tmdb.data.model.*
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.QueryMap


/**
 * Created by luis rafael on 16/02/19.
 */
interface NewsApi {

    fun getParams(page: Int): MutableMap<String, String>

    fun getParamsSearch(page: Int, query: String): MutableMap<String, String>

    fun getGenders(url: String, params: MutableMap<String, String>): Single<ObjectGenders>

    fun getMovies(url: String, params: MutableMap<String, String>): Single<ObjectMovies>

    fun getTvShows(url: String, params: MutableMap<String, String>): Single<ObjectTv>

    fun getImages(url: String, params: MutableMap<String, String>): Single<ObjectImages>

    fun getTrailer(url: String, params: MutableMap<String, String>): Single<ObjectTrailers>

    fun getDetailsMovies(id: Int, params: MutableMap<String, String>): Single<PojoDetailsMovies>

    fun getDetailsTv(id: Int, params: MutableMap<String, String>): Single<PojoDetailsTv>

    fun getDetailsPerson(id: Int, params: MutableMap<String, String>): Single<PojoDetailsPerson>

    fun getSimilarMovie(id: Int, params: MutableMap<String, String>): Single<ObjectMovies>

    fun getSimilarTv(id: Int, params: MutableMap<String, String>): Single<ObjectTv>

    fun getDataSearch(params: MutableMap<String, String>): Single<ObjectsSearch>

    fun getCast(url: String, params: MutableMap<String, String>): Single<ObjectsCast>

    fun getPersons(params: MutableMap<String, String>) :Single<ObjectPerson>

    fun getReview(url:String, params: MutableMap<String, String>) : Single<ObjectReviews>

    fun getExternalIds(url: String,params: MutableMap<String, String>) : Single<ObjectExternalIds>

    fun getImagesPerson(path: String,params: MutableMap<String, String>) : Single<ObjectImagesPerson>

    fun getImagesTaggedPerson(path: String,params: MutableMap<String, String>) : Single<ObjectImagesPersonTagged>

    fun getMovieCredits(path: String,params: MutableMap<String, String>) : Single<ObjectMoviePerson>

    fun getTvCredits(path: String,params: MutableMap<String, String>) : Single<ObjectTvPerson>

}