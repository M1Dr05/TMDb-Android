package com.github.midros.tmdb.app

import android.app.Application
import com.github.midros.tmdb.di.components.AppsComponent
import com.github.midros.tmdb.di.components.DaggerAppsComponent
import com.github.midros.tmdb.di.modules.AppModule
import com.github.midros.tmdb.di.modules.NetModule

/**
 * Created by luis rafael on 16/02/19.
 */
class TmdbApp : Application() {

    companion object {
        @JvmStatic lateinit var component: AppsComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppsComponent.builder().netModule(NetModule(this)).appModule(AppModule(this)).build()
        component.inject(this)
    }


}