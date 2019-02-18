package com.github.midros.tmdb.utils

/**
 * Created by luis rafael on 16/02/19.
 */
class ConstStrings {

    companion object {

        const val TAG = "TMDB"

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val URL_TMDB = "https://www.themoviedb.org/"

        const val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/"

        const val BASE_URL_IMAGE_YOUTUBE = "https://img.youtube.com/vi/"
        const val SIZE_IMG_YOUTUBE = "/mqdefault.jpg"
        const val BASE_URL_APP_YOUTUBE = "vnd.youtube:"
        const val BASE_URL_BROWSER_YOUTUBE = "http://www.youtube.com/watch?v="

        const val BASE_URL_APP_FB = "fb://facewebmodal/f?href="
        const val BASE_URL_BROWSER_FB = "https://www.facebook.com/"

        const val BASE_URL_APP_TW = "twitter://user?screen_name="
        const val BASE_URL_BROWSER_TW = "https://twitter.com/"

        const val BASE_URL_APP_IT = "http://instagram.com/_u/"
        const val BASE_URL_BROWSER_IT = "https://instagram.com/"

        const val BASE_URL_BROWSER_IMDB = "https://www.imdb.com/title/"

        const val POPULAR_MOVIE = "movie/popular"
        const val TOP_RATED_MOVIE = "movie/top_rated"
        const val UPCOMING_MOVIE = "movie/upcoming"
        const val NOW_PLAYING = "movie/now_playing"

        const val POPULAR_TV = "tv/popular"
        const val TOP_RATED_TV = "tv/top_rated"
        const val AIR_TV = "tv/on_the_air"
        const val TODAY_TV = "tv/airing_today"

        const val SEARCH = "search/multi"

        const val POPULAR_PERSON ="person/popular"
        const val IMAGES_PERSON = "person/{person_id}/images"
        const val IMAGES_TAGGED_PERSON = "person/{person_id}/tagged_images"
        const val MOVIE_CREDITS_PERSON ="person/{person_id}/movie_credits"
        const val TV_CREDITS_PERSON = "person/{person_id}/tv_credits"

        const val GENDER_MOVIE = "genre/movie/list"
        const val GENDER_TV = "genre/tv/list"

        const val SIMILAR_MOVIE = "movie/{movie_id}/similar"
        const val SIMILAR_TV = "tv/{tv_id}/similar"

        const val DETAILS_MOVIE = "movie/{movie_id}"
        const val DETAILS_TV = "tv/{tv_id}"
        const val DETAILS_PERSON = "person/{person_id}"

        const val MOVIE_ID = "movie_id"
        const val TV_ID = "tv_id"
        const val PERSON_ID = "person_id"

        const val SIZE_IMAGE_POSTER = "w154"
        const val SIZE_IMAGE_BACKDROP = "w500"
        const val SIZE_IMAGE_W780 = "w780"

        const val LANGUAGE = "language"
        const val PAGE = "page"
        const val QUERY = "query"
        const val API_KEY = "api_key"
        const val REGION = "region"

        const val CATEGORY = "category"

        const val MOVIE = "movie"
        const val TV = "tv"
        const val PERSON = "person"

        const val DEFAULT = "default"
        const val HORIZONTAL = "horizontal"
        const val COUNT3 = "count3"

        const val TYPE_FRAGMENT = "fragment"
        const val SEARCH_TAG = "searchFragment"

        const val ID = "id"
        const val TITLE = "title"
        const val TYPE = "type"
        const val IMAGE = "image"

    }
}