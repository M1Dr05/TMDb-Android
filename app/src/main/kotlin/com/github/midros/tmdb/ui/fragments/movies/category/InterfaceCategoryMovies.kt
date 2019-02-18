package com.github.midros.tmdb.ui.fragments.movies.category

import com.github.midros.tmdb.data.model.ObjectMovies
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView
import io.reactivex.Single

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceCategoryMovies {

    interface Interactor<V : ViewCategoryMovies> : InterfaceInteractor<V> {
        fun setCategory(category: String)
        fun getParams(): MutableMap<String, String>
        fun getMovies()
        fun getPage() : Int
    }

    interface ViewCategoryMovies : InterfaceView {
        fun setAdapterRecycler()
        fun addItemRecycler(data:ObjectMovies)
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()
    }
}