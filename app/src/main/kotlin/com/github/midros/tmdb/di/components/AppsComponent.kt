package com.github.midros.tmdb.di.components

import com.github.midros.tmdb.app.TmdbApp
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.di.modules.NetModule
import dagger.Component
import javax.inject.Singleton


/**
 * Created by luis rafael on 16/02/19.
 */
@Singleton
@Component(modules = [NetModule::class])
interface AppsComponent {
    fun inject(app: TmdbApp)
    fun getNewsApi() : NewsApi
}