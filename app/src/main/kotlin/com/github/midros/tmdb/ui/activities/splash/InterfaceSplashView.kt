package com.github.midros.tmdb.ui.activities.splash

import com.github.midros.tmdb.data.model.ObjectGenders
import com.github.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceSplashView : InterfaceView{

    fun openActivityHome()
    fun setGenderTv(data: ObjectGenders)
    fun setGenderMovies(data:ObjectGenders)

}