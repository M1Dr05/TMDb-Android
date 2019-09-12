package com.github.midros.tmdb.ui.activities.detailspeople

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.e
import com.github.midros.tmdb.utils.ConstFun.getApiKeyTmdb
import com.github.midros.tmdb.utils.schedulers.SchedulerProvider
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorDetailsPeople<V : InterfaceDetailsPeople.View> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceDetailsPeople.Interactor<V> {

    override fun getParams(): MutableMap<String, String> = getApi().getParams(1)

    override fun getDetailPeople(id:Int) {
        getView().addDisposable(getApi().getDetailsPerson(id,getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoadingData() }
                .subscribe({ if (isViewNull()) getView().setDataPeople(it) }, { if (isViewNull()) getView().failedData() }))
    }

    override fun getImagesTagged(id:Int) {
        getView().addDisposable(getApi().getImagesTaggedPerson(id.toString(),getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoadingTagged() }
                .subscribe({ if (isViewNull()) getView().setDataImagesTagged(it.results) }, { if (isViewNull()) getView().failedTagged() }))
    }

    override fun getImagesPeople(id: Int) {
        val params = mutableMapOf<String, String>()
        params[ConstStrings.API_KEY] = getApiKeyTmdb()
        getView().addDisposable(getApi().getImagesPerson(id.toString(), params)
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearImagesPerson() }
                .subscribe({ if (isViewNull()) getView().setDataImagesPeople(it) }, { e(it) }))
    }

    override fun getMovieCredits(id: Int) {
        getView().addDisposable(getApi().getMovieCredits(id.toString(),getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if ((isViewNull())) getView().clearMovieCredits() }
                .subscribe({ if (isViewNull()) getView().setDataMovieCredits(it.cast) },{ e(it) }))
    }

    override fun getTvCredits(id: Int) {
        getView().addDisposable(getApi().getTvCredits(id.toString(),getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().clearTvCredits() }
                .subscribe({ if (isViewNull()) getView().setDataTvCredits(it.cast) },{ e(it) }))
    }

    override fun getExternalsIds(url: String) {
        getView().addDisposable(getApi().getExternalIds(url,getParams())
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe({ if (isViewNull()) getView().setDataExternalIds(it) }, { e(it) }))
    }
}