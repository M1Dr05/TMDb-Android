package com.github.midros.tmdb.ui.activities.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.api.NewsApi
import com.github.midros.tmdb.ui.activities.base.BaseInteractor
import com.github.midros.tmdb.ui.fragments.home.FragmentHome
import com.github.midros.tmdb.ui.fragments.movies.FragmentMovies
import com.github.midros.tmdb.ui.fragments.people.FragmentPeople
import com.github.midros.tmdb.ui.fragments.search.FragmentSearch
import com.github.midros.tmdb.ui.fragments.tvshows.FragmentTvShow
import com.github.midros.tmdb.utils.ConstStrings
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorMain<V : InterfaceMainView> @Inject constructor(supportFragment: FragmentManager, context: Context, api: NewsApi) : BaseInteractor<V>(supportFragment, context,api), InterfaceMainInteractor<V> {

    private var fragmentSearch = FragmentSearch()
    private var fragmentHome = FragmentHome()
    private var fragmentMovies = FragmentMovies()
    private var fragmentTvShow = FragmentTvShow()

    override fun setFragmentHome() {
        getView().setDrawerUnLock()
        getView().setCheckedNavigationItem(R.id.nav_home)
        getView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.home))
        setFragment(fragmentHome)
    }

    override fun setFragmentMovies() {
        getView().setCheckedNavigationItem(R.id.nav_movies)
        getView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.movies))
        setFragment(fragmentMovies)
    }

    override fun setFragmentSeries() {
        getView().setCheckedNavigationItem(R.id.nav_tv_shows)
        getView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.tv_shows))
        setFragment(fragmentTvShow)
    }

    override fun setFragmentPeople() {
        getView().setCheckedNavigationItem(R.id.nav_people)
        getView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.people))
        setFragment(FragmentPeople())
    }

    override fun setFragmentSearch() {
        getView().setDrawerLock()
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.frame_principal, fragmentSearch, ConstStrings.SEARCH_TAG)
                .commit()
    }

    override fun closeFragmentSearch(tag: String) {
        val fragmentManager = getSupportFragmentManager()
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .remove(fragment)
                    .commitNow()
            getView().setDrawerUnLock()
        }
    }

    override fun getFragmentSearch(): FragmentSearch = fragmentSearch
    override fun getFragmentHome(): FragmentHome = fragmentHome
    override fun getFragmentMovies(): FragmentMovies = fragmentMovies
    override fun getFragmentTvShow(): FragmentTvShow = fragmentTvShow

    private fun setTitleToolbar(title: String) {
        getView().setTitleToolbar(title)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = getSupportFragmentManager()
        val fragmentNow = fragmentManager.findFragmentByTag(ConstStrings.TYPE_FRAGMENT)
        val trans = fragmentManager.beginTransaction()
        if (fragmentNow != null) {
            trans.remove(fragmentNow)
        }
        trans.disallowAddToBackStack()
        trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        trans.replace(R.id.frame_principal, fragment, ConstStrings.TYPE_FRAGMENT)
        trans.commitNow()

    }
}