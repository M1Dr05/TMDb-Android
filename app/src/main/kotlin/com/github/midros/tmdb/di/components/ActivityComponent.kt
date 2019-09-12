package com.github.midros.tmdb.di.components

import com.github.midros.tmdb.ui.activities.main.MainActivity
import com.github.midros.tmdb.ui.activities.splash.SplashActivity
import com.github.midros.tmdb.di.PerActivity
import com.github.midros.tmdb.di.modules.ActivityModule
import com.github.midros.tmdb.ui.activities.image.ViewPagerImageActivity
import com.github.midros.tmdb.ui.activities.detailspeople.DetailsPeopleActivity
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsBottom
import com.github.midros.tmdb.ui.fragments.details.FragmentDetailsTop
import com.github.midros.tmdb.ui.fragments.home.FragmentHome
import com.github.midros.tmdb.ui.fragments.movies.category.FragmentCategoryMovies
import com.github.midros.tmdb.ui.fragments.people.FragmentPeople
import com.github.midros.tmdb.ui.fragments.search.FragmentSearch
import com.github.midros.tmdb.ui.fragments.tvshows.category.FragmentCategoryTv
import dagger.Component


/**
 * Created by luis rafael on 16/02/19.
 */
@PerActivity
@Component(dependencies = [AppsComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(detailsPeopleActivity: DetailsPeopleActivity)

    fun inject(viewPagerImageActivity: ViewPagerImageActivity)

    fun inject(fragmentDetailsTop: FragmentDetailsTop)

    fun inject(fragmentDetailsBottom: FragmentDetailsBottom)

    fun inject(fragmentCategoryMovies: FragmentCategoryMovies)

    fun inject(fragmentCategoryTv: FragmentCategoryTv)

    fun inject(fragmentSearch: FragmentSearch)

    fun inject(fragmentPeople: FragmentPeople)

    fun inject(fragmentHome: FragmentHome)

}