package com.github.midros.tmdb.data.api

import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.getApiKeyTmdb
import com.github.midros.tmdb.utils.getCountry
import com.github.midros.tmdb.utils.getLocale
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by luis rafael on 16/02/19.
 */
class RestFull @Inject constructor(private val restApi: RestApi) : NewsApi {

    override fun getParams(page: Int): MutableMap<String, String> {
        val params = mutableMapOf<String, String>()
        params[ConstStrings.API_KEY] = getApiKeyTmdb()
        params[ConstStrings.LANGUAGE] = getLocale()
        params[ConstStrings.PAGE] = page.toString()
        params[ConstStrings.REGION] = getCountry()
        return params
    }

    override fun getParamsSearch(page: Int, query: String): MutableMap<String, String> {
        val params = mutableMapOf<String, String>()
        params[ConstStrings.API_KEY] = getApiKeyTmdb()
        params[ConstStrings.LANGUAGE] = getLocale()
        params[ConstStrings.PAGE] = page.toString()
        params[ConstStrings.REGION] = getCountry()
        params[ConstStrings.QUERY] = query
        return params
    }

    override fun getGenders(url: String, params: MutableMap<String, String>): Single<ObjectGenders>
            = restApi.getGenders(url, params)

    override fun getMovies(url: String, params: MutableMap<String, String>): Single<ObjectMovies>
            = restApi.getMovies(url, params)

    override fun getTvShows(url: String, params: MutableMap<String, String>): Single<ObjectTv>
            = restApi.getTvShows(url, params)

    override fun getImages(url: String, params: MutableMap<String, String>): Single<ObjectImages>
            = restApi.getImages(url, params)

    override fun getTrailer(url: String, params: MutableMap<String, String>): Single<ObjectTrailers>
            = restApi.getTrailer(url, params)

    override fun getDetailsMovies(id: Int, params: MutableMap<String, String>): Single<PojoDetailsMovies>
            = restApi.getDetailsMovies(id, params)

    override fun getDetailsTv(id: Int, params: MutableMap<String, String>): Single<PojoDetailsTv>
            = restApi.getDetailsTv(id, params)

    override fun getDetailsPerson(id: Int, params: MutableMap<String, String>): Single<PojoDetailsPerson>
            = restApi.getDetailsPerson(id, params)

    override fun getSimilarMovie(id: Int, params: MutableMap<String, String>): Single<ObjectMovies>
            = restApi.getSimilarMovie(id, params)

    override fun getSimilarTv(id: Int, params: MutableMap<String, String>): Single<ObjectTv>
            = restApi.getSimilarTv(id, params)

    override fun getDataSearch(params: MutableMap<String, String>): Single<ObjectsSearch>
            = restApi.getDataSearch(params)

    override fun getCast(url: String, params: MutableMap<String, String>): Single<ObjectsCast>
            = restApi.getCast(url, params)

    override fun getPersons(params: MutableMap<String, String>): Single<ObjectPerson>
            = restApi.getPersons(params)

    override fun getReview(url: String, params: MutableMap<String, String>): Single<ObjectReviews>
            = restApi.getReview(url,params)

    override fun getExternalIds(url: String, params: MutableMap<String, String>): Single<ObjectExternalIds>
            = restApi.getExternalIds(url,params)

    override fun getImagesPerson(path: String, params: MutableMap<String, String>): Single<ObjectImagesPerson>
            = restApi.getImagesPerson(path,params)

    override fun getImagesTaggedPerson(path: String, params: MutableMap<String, String>): Single<ObjectImagesPersonTagged>
            = restApi.getImagesTaggedPerson(path,params)

    override fun getMovieCredits(path: String, params: MutableMap<String, String>): Single<ObjectMoviePerson> =
            restApi.getMovieCredits(path,params)

    override fun getTvCredits(path: String, params: MutableMap<String, String>): Single<ObjectTvPerson> =
            restApi.getTvCredits(path,params)

}