package com.github.midros.tmdb.ui.activities.base

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.data.api.NewsApi

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceInteractor<V : InterfaceView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getView(): V

    fun isViewNull() : Boolean

    fun getContext(): Context

    fun getSupportFragmentManager(): FragmentManager

    fun getApi() : NewsApi

}