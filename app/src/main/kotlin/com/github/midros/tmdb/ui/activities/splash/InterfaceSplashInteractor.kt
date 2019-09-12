package com.github.midros.tmdb.ui.activities.splash

import com.github.midros.tmdb.di.PerActivity
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor

/**
 * Created by luis rafael on 16/02/19.
 */
@PerActivity
interface InterfaceSplashInteractor<V : InterfaceSplashView> : InterfaceInteractor<V> {
    fun onGetGendersMovies()
    fun onGetGendersTv()
}