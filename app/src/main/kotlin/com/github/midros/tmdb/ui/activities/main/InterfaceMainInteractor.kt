package com.github.midros.tmdb.ui.activities.main

import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.di.PerActivity
import com.github.midros.tmdb.ui.fragments.home.FragmentHome
import com.github.midros.tmdb.ui.fragments.movies.FragmentMovies
import com.github.midros.tmdb.ui.fragments.search.FragmentSearch
import com.github.midros.tmdb.ui.fragments.tvshows.FragmentTvShow

/**
 * Created by luis rafael on 16/02/19.
 */
@PerActivity
interface InterfaceMainInteractor<V : InterfaceMainView> : InterfaceInteractor<V> {

    fun setFragmentHome()
    fun setFragmentMovies()
    fun setFragmentSeries()
    fun setFragmentPeople()
    fun setFragmentSearch()
    fun closeFragmentSearch(tag: String)
    fun getFragmentSearch(): FragmentSearch
    fun getFragmentHome() : FragmentHome
    fun getFragmentMovies() : FragmentMovies
    fun getFragmentTvShow() : FragmentTvShow

}