package com.github.midros.tmdb.ui.fragments.home

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.utils.ConstStrings.Companion.AIR_TV
import com.github.midros.tmdb.utils.ConstStrings.Companion.NOW_PLAYING
import com.github.midros.tmdb.utils.ConstStrings.Companion.POPULAR_MOVIE
import com.github.midros.tmdb.utils.ConstFun.e
import com.github.midros.tmdb.utils.schedulers.SchedulerProvider
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorFragmentHome<V : InterfaceFragmentHome.View> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceFragmentHome.Interactor<V> {

    override fun getParams(): MutableMap<String, String> = getApi().getParams(1)

    override fun getMoviesPopular() {
        getView().addDisposable(getApi().getMovies(POPULAR_MOVIE, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoadingMoviesPopular() }
                .doFinally { if (isViewNull()) getView().hideLoadingMoviesPopular() }
                .subscribe({ if (isViewNull()) getView().setDataMoviesPopular(it.results) }, { e(it) }))
    }

    override fun getMoviesNowPlaying() {
        getView().addDisposable(getApi().getMovies(NOW_PLAYING, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoadingMoviesNowPlaying() }
                .doFinally { if (isViewNull()) getView().hideLoadingMoviesNowPlaying() }
                .subscribe({ if (isViewNull()) getView().setDataMoviesNowPlaying(it.results) }, { e(it) }))
    }

    override fun getOnTv() {
        getView().addDisposable(getApi().getTvShows(AIR_TV,getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoadingOnTv() }
                .doFinally { if (isViewNull()) getView().hideLoadingOntTv() }
                .subscribe({ if (isViewNull()) getView().setDataOnTv(it.results) }, { e(it) }))
    }

}