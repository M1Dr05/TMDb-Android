package com.github.midros.tmdb.ui.fragments.search

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.utils.schedulers.SchedulerProvider
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorSearch<V : InterfaceSearch.View> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceSearch.Interactor<V> {

    override fun getParamsSearch(query: String): MutableMap<String, String> = getApi().getParamsSearch(1, query)

    override fun getDataSearch(query: String){
        getView().addDisposable(getApi().getDataSearch(getParamsSearch(query))
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .subscribe({ if (isViewNull()) getView().resultData(it) }, { if (isViewNull()) getView().hiddenLoadingFailed() }))
    }

}