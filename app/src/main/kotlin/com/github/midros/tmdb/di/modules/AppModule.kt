package com.github.midros.tmdb.di.modules

import android.app.Application
import android.content.Context
import com.github.midros.tmdb.app.TmdbApp
import com.github.midros.tmdb.di.AppsContext
import dagger.Module
import dagger.Provides


/**
 * Created by luis rafael on 16/02/19.
 */
@Module
class AppModule(val app: TmdbApp) {

    @Provides
    @AppsContext
    fun provideContext(): Context = app

    @Provides
    fun provideApplication(): Application = app

}