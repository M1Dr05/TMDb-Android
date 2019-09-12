package com.github.midros.tmdb.ui.fragments.search

import com.github.midros.tmdb.data.model.ObjectsSearch
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView
import com.google.gson.JsonArray
import io.reactivex.Single

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceSearch {

    interface Interactor<V : View> : InterfaceInteractor<V> {
        fun getParamsSearch(query: String): MutableMap<String, String>
        fun getDataSearch(query: String)
    }

    interface View : InterfaceView {
        fun setSearch(query: String)
        fun setDataSearch(json: JsonArray)
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()
        fun hiddenLoadingNotData()
        fun resultData(data:ObjectsSearch)
    }

}