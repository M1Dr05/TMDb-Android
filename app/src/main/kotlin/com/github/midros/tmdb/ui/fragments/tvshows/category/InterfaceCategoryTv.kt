package com.github.midros.tmdb.ui.fragments.tvshows.category

import com.github.midros.tmdb.data.model.ObjectTv
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView
import io.reactivex.Single

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceCategoryTv {


    interface Interactor<V : ViewCategoryTv> : InterfaceInteractor<V> {
        fun setCategory(category: String)
        fun getParams(): MutableMap<String, String>
        fun getTvShow()
        fun getPage() : Int
    }

    interface ViewCategoryTv : InterfaceView {
        fun setAdapterRecycler()
        fun addItemRecycler(data:ObjectTv)
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()

    }

}