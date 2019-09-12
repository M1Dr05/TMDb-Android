package com.github.midros.tmdb.ui.fragments.movies.category

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.utils.schedulers.SchedulerProvider
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorCategoryMovies<V : InterfaceCategoryMovies.ViewCategoryMovies> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceCategoryMovies.Interactor<V> {

    private var category: String? = null
    private var nextPage: Int = 1

    override fun getPage(): Int = nextPage

    override fun setCategory(category: String) {
        this.category = category
    }

    override fun getParams(): MutableMap<String, String> = getApi().getParams(nextPage)

    override fun getMovies() {
        getView().addDisposable(getApi().getMovies(category!!, getParams())
                .doOnSuccess { nextPage = it.page + 1 }
                .subscribeOn(SchedulerProvider.io())
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (isViewNull()) getView().showLoading() }
                //.doFinally { if (isViewNull()) getView().hiddenLoading() }
                .subscribe({ if (isViewNull()) getView().addItemRecycler(it) }, { if (isViewNull()) getView().hiddenLoadingFailed() }))
    }

}