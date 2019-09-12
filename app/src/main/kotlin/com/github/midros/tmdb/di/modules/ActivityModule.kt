package com.github.midros.tmdb.di.modules

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.github.midros.tmdb.ui.activities.main.InteractorMain
import com.github.midros.tmdb.ui.activities.main.InterfaceMainInteractor
import com.github.midros.tmdb.ui.activities.main.InterfaceMainView
import com.github.midros.tmdb.di.PerActivity
import com.github.midros.tmdb.ui.activities.splash.InteractorSplash
import com.github.midros.tmdb.ui.activities.splash.InterfaceSplashInteractor
import com.github.midros.tmdb.ui.activities.splash.InterfaceSplashView
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsBottom
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsTop
import com.github.midros.tmdb.ui.fragments.details.InteractorFragmentDetails
import com.github.midros.tmdb.ui.fragments.details.InterfaceFragmentDetails
import com.github.midros.tmdb.ui.fragments.movies.category.InteractorCategoryMovies
import com.github.midros.tmdb.ui.fragments.movies.category.InterfaceCategoryMovies
import com.github.midros.tmdb.ui.fragments.people.InteractorFragmentPeople
import com.github.midros.tmdb.ui.fragments.people.InterfacePeople
import com.github.midros.tmdb.ui.fragments.search.InteractorSearch
import com.github.midros.tmdb.ui.fragments.search.InterfaceSearch
import com.github.midros.tmdb.ui.fragments.tvshows.category.InteractorCategoryTv
import com.github.midros.tmdb.ui.fragments.tvshows.category.InterfaceCategoryTv
import com.github.midros.tmdb.utils.ConstStrings
import dagger.Module
import dagger.Provides
import javax.inject.Named


/**
 * Created by luis rafael on 16/02/19.
 */
@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context = activity.applicationContext

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideSupportFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    fun provideFragmentDetailsTop(): FragmentDetailsTop = FragmentDetailsTop()

    @Provides
    fun provideFragmentDetailsBottom(): FragmentDetailsBottom = FragmentDetailsBottom()

    @Provides
    @PerActivity
    fun provideInterfaceMainInteractor(interactor: InteractorMain<InterfaceMainView>): InterfaceMainInteractor<InterfaceMainView> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceSplashInteractor(interactor: InteractorSplash<InterfaceSplashView>): InterfaceSplashInteractor<InterfaceSplashView> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceCategoryMoviesInteractor(interactor: InteractorCategoryMovies<InterfaceCategoryMovies.ViewCategoryMovies>): InterfaceCategoryMovies.Interactor<InterfaceCategoryMovies.ViewCategoryMovies> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceCategoryTvInteractor(interactor: InteractorCategoryTv<InterfaceCategoryTv.ViewCategoryTv>): InterfaceCategoryTv.Interactor<InterfaceCategoryTv.ViewCategoryTv> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceSearchInteractor(interactor: InteractorSearch<InterfaceSearch.View>): InterfaceSearch.Interactor<InterfaceSearch.View> = interactor

    @Provides
    @PerActivity
    fun provideInterfaceFragmentDetailsInteractor(interactor: InteractorFragmentDetails<InterfaceFragmentDetails.View>): InterfaceFragmentDetails.Interactor<InterfaceFragmentDetails.View> = interactor

    @Provides
    @PerActivity
    fun provideInterfacePeopleInteractor(interactor: InteractorFragmentPeople<InterfacePeople.View>): InterfacePeople.Interactor<InterfacePeople.View> = interactor

    @Provides
    @Named(ConstStrings.DEFAULT)
    fun provideGridLayoutManager(context: Context): GridLayoutManager = GridLayoutManager(context, 1)

    @Provides
    @Named(ConstStrings.HORIZONTAL)
    fun provideGridLayoutManagerHorizontal(context: Context): GridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)

    @Provides
    @Named(ConstStrings.COUNT3)
    fun provideGridLayoutManagerCount3(context: Context): GridLayoutManager = GridLayoutManager(context, 3)

}