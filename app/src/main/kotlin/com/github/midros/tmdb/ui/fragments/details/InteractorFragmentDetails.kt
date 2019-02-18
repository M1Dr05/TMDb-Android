package com.github.midros.tmdb.ui.fragments.details

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.data.model.PojoDetailsPerson
import com.github.midros.tmdb.data.model.ObjectsCast
import com.github.midros.tmdb.data.model.ObjectImages
import com.github.midros.tmdb.data.model.ObjectMovies
import com.github.midros.tmdb.data.model.PojoDetailsMovies
import com.github.midros.tmdb.data.model.ObjectTrailers
import com.github.midros.tmdb.data.model.ObjectTv
import com.github.midros.tmdb.data.model.PojoDetailsTv
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.e
import com.github.midros.tmdb.utils.getApiKeyTmdb
import com.github.midros.tmdb.utils.schedulers.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorFragmentDetails<V : InterfaceFragmentDetails.View> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceFragmentDetails.Interactor<V> {

    override fun getParams(): MutableMap<String, String> = getApi().getParams(1)

    override fun getDetailsMovie(id: Int){
        getView().addDisposable(getApi().getDetailsMovies(id, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoading() }
                .doFinally { if (isViewNull()) getView().hiddenLoading() }
                .subscribe({ if (isViewNull()) getView().setDataMovies(it) }, { if (isViewNull()) getView().hiddenFailed() }))
    }

    override fun getDetailsTv(id: Int){
        getView().addDisposable(getApi().getDetailsTv(id, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoading() }
                .doFinally { if (isViewNull()) getView().hiddenLoading() }
                .subscribe({ if (isViewNull()) getView().setDataTv(it) }, { if (isViewNull()) getView().hiddenFailed() }))
    }

    override fun getExternalIds(url: String) {
        getView().addDisposable(getApi().getExternalIds(url,getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe({ if (isViewNull()) getView().setDataExternalIds(it) }, { e(it) }))
    }

    override fun getReviews(url: String) {
        getView().addDisposable(getApi().getReview(url, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearListReviews() }
                .subscribe({ if (isViewNull()) getView().setReviews(it.results) }, { e(it) }))
    }

    override fun getCast(url: String){
        getView().addDisposable(getApi().getCast(url, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearListCast() }
                .subscribe({ if (isViewNull()) getView().setCast(it.cast) }, { e(it) }))
    }

    override fun getTrailers(url: String){
        getView().addDisposable(getApi().getTrailer(url, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe {
                    if (isViewNull()) getView().showLoading()
                    if (isViewNull()) getView().clearListTrailers()
                }
                .subscribe({
                    if (isViewNull()) getView().setDataTrailer(it)
                    if (isViewNull()) getView().setTrailers(it.results)
                }, { if (isViewNull()) getView().hiddenFailed() }))
    }

    override fun getSimilarMovie(id: Int) {
        getView().addDisposable(getApi().getSimilarMovie(id, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearListSimilarMovies() }
                .subscribe({ if (isViewNull()) getView().setDataSimilarMovie(it.results) }, { e(it) }))
    }

    override fun getSimilarTv(id: Int) {
        getView().addDisposable(getApi().getSimilarTv(id, getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearListSimilarTv() }
                .subscribe({ if (isViewNull()) getView().setDataSimilarTv(it.results) }, { e(it) }))
    }

    override fun getImages(url: String) {
        val params = mutableMapOf<String, String>()
        params[ConstStrings.API_KEY] = getApiKeyTmdb()
        getView().addDisposable(getApi().getImages(url, params)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearListImages() }
                .subscribe({ if (isViewNull()) getView().setImages(it) }, { e(it) }))
    }

}