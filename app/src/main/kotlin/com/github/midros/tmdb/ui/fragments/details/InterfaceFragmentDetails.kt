package com.github.midros.tmdb.ui.fragments.details

import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView
import io.reactivex.Single

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceFragmentDetails {

    interface Interactor<V : View> : InterfaceInteractor<V> {
        fun getParams(): MutableMap<String, String>
        fun getDetailsMovie(id: Int)
        fun getDetailsTv(id: Int)
        fun getCast(url: String)
        fun getTrailers(url: String)
        fun getImages(url: String)
        fun getSimilarMovie(id: Int)
        fun getSimilarTv(id: Int)
        fun getReviews(url:String)
        fun getExternalIds(url: String)
    }

    interface View : InterfaceView{
        fun setDetailsTop(id: Int, name: String, type: String, image: String)
        fun setCueVideo(key: String)

        fun showLoading()
        fun hiddenLoading()
        fun hiddenFailed()

        fun setImage()
        fun setTrailer(data: ObjectDetailsTrailers)
        fun setPausePlayer()
        fun showMenuDetailsTop()
        fun hiddenMenuDetailsTop()
        fun showPlayer()
        fun hiddenViewsPlayer()
        fun showViewsPlayer()

        fun getIdDetails(): Int
        fun showDataDetails()
        fun hiddenViews()

        fun setDataTrailer(data: ObjectTrailers)
        fun setDataMovies(data: PojoDetailsMovies)
        fun setDataTv(data: PojoDetailsTv)
        fun setDataExternalIds(data: ObjectExternalIds)

        fun setCast(list: MutableList<ObjectsDetailsCast>)
        fun setReviews(list: MutableList<ObjectDetailsReviews>)
        fun setTrailers(list: MutableList<ObjectDetailsTrailers>)
        fun setImages(data: ObjectImages)

        fun setDataSimilarMovie(list: MutableList<PojoResultsMovie>)
        fun setDataSimilarTv(list: MutableList<PojoResultsTv>)

        fun clearListCast()
        fun clearListTrailers()
        fun clearListSimilarMovies()
        fun clearListSimilarTv()
        fun clearListImages()
        fun clearListReviews()
    }
}