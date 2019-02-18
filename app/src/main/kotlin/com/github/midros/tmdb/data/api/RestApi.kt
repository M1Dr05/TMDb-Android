package com.github.midros.tmdb.data.api

import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.utils.ConstStrings.Companion.DETAILS_MOVIE
import com.github.midros.tmdb.utils.ConstStrings.Companion.DETAILS_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.DETAILS_TV
import com.github.midros.tmdb.utils.ConstStrings.Companion.IMAGES_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.IMAGES_TAGGED_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.MOVIE_CREDITS_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.MOVIE_ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.PERSON_ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.POPULAR_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.SEARCH
import com.github.midros.tmdb.utils.ConstStrings.Companion.SIMILAR_MOVIE
import com.github.midros.tmdb.utils.ConstStrings.Companion.SIMILAR_TV
import com.github.midros.tmdb.utils.ConstStrings.Companion.TV_CREDITS_PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.TV_ID
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by luis rafael on 16/02/19.
 */
interface RestApi {

    @GET
    fun getGenders(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectGenders>

    @GET(POPULAR_PERSON)
    fun getPersons(@QueryMap params: MutableMap<String, String>) :Single<ObjectPerson>

    @GET
    fun getMovies(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectMovies>

    @GET
    fun getTvShows(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectTv>

    @GET
    fun getImages(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectImages>

    @GET
    fun getTrailer(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectTrailers>

    @GET(DETAILS_MOVIE)
    fun getDetailsMovies(@Path(MOVIE_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<PojoDetailsMovies>

    @GET(DETAILS_TV)
    fun getDetailsTv(@Path(TV_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<PojoDetailsTv>

    @GET(DETAILS_PERSON)
    fun getDetailsPerson(@Path(PERSON_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<PojoDetailsPerson>

    @GET(SIMILAR_MOVIE)
    fun getSimilarMovie(@Path(MOVIE_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<ObjectMovies>

    @GET(SIMILAR_TV)
    fun getSimilarTv(@Path(TV_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<ObjectTv>

    @GET(SEARCH)
    fun getDataSearch(@QueryMap params: MutableMap<String, String>): Single<ObjectsSearch>

    @GET
    fun getCast(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectsCast>

    @GET
    fun getReview(@Url url:String,@QueryMap params: MutableMap<String, String>) : Single<ObjectReviews>

    @GET
    fun getExternalIds(@Url url:String,@QueryMap params: MutableMap<String, String>) : Single<ObjectExternalIds>

    @GET(IMAGES_PERSON)
    fun getImagesPerson(@Path("person_id") person_id:String,@QueryMap params: MutableMap<String, String>) : Single<ObjectImagesPerson>

    @GET(IMAGES_TAGGED_PERSON)
    fun getImagesTaggedPerson(@Path("person_id") person_id:String, @QueryMap params: MutableMap<String, String>) : Single<ObjectImagesPersonTagged>

    @GET(MOVIE_CREDITS_PERSON)
    fun getMovieCredits(@Path("person_id") person_id: String,@QueryMap params: MutableMap<String, String>) : Single<ObjectMoviePerson>

    @GET(TV_CREDITS_PERSON)
    fun getTvCredits(@Path("person_id") person_id: String,@QueryMap params: MutableMap<String, String>) : Single<ObjectTvPerson>


}