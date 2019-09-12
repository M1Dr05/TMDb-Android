package com.github.midros.tmdb.ui.fragments.home

import com.github.midros.tmdb.data.model.PojoResultsMovie
import com.github.midros.tmdb.data.model.PojoResultsTv
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceFragmentHome {

    interface Interactor<V : View> : InterfaceInteractor<V>{

        fun getMoviesPopular()
        fun getMoviesNowPlaying()
        fun getOnTv()
        fun getParams(): MutableMap<String, String>

    }

    interface View : InterfaceView{
        fun showLoadingMoviesPopular()
        fun hideLoadingMoviesPopular()
        fun setDataMoviesPopular(list: MutableList<PojoResultsMovie>)

        fun showLoadingMoviesNowPlaying()
        fun hideLoadingMoviesNowPlaying()
        fun setDataMoviesNowPlaying(list: MutableList<PojoResultsMovie>)

        fun showLoadingOnTv()
        fun hideLoadingOntTv()
        fun setDataOnTv(list: MutableList<PojoResultsTv>)
    }

}