package com.github.midros.tmdb.ui.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.utils.ConstFun.removeShiftMode
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentMovies : BaseFragment() {

    lateinit var interactor: InteractorFragmentMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interactor = InteractorFragmentMovies(childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setNavigationButton()
    }

    fun setItemSelectedNavigation(itemId : Int){
        if (itemId!=0) navigation_movies.selectedItemId = itemId
    }

    fun onSelectedCategoryPopular(){
        interactor.fragmentCategoryPopular()
    }

    private fun setNavigationButton() {
        removeShiftMode(navigation_movies)
        navigation_movies.setOnNavigationItemReselectedListener {}
        navigation_movies.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular_movies -> {
                    interactor.fragmentCategoryPopular()
                }
                R.id.nav_top_rating_movies -> {
                    interactor.fragmentCategoryRating()
                }
                R.id.nav_upcoming_movies -> {
                    interactor.fragmentCategoryUpcoming()
                }
                R.id.nav_now_playing -> {
                    interactor.fragmentCategoryNowPlaying()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


}