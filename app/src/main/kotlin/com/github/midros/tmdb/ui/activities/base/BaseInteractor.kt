package com.github.midros.tmdb.ui.activities.base

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
open class BaseInteractor<V : InterfaceView> @Inject constructor(private var supportFragment: FragmentManager, private var context: Context, private val api: NewsApi) : InterfaceInteractor<V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun isViewNull(): Boolean = view!=null

    override fun getView(): V = view!!

    override fun getContext(): Context = context

    override fun getSupportFragmentManager(): FragmentManager = supportFragment

    override fun getApi(): NewsApi = api

}