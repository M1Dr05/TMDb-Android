package com.github.midros.tmdb.ui.fragments.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.utils.removeShiftMode
import kotlinx.android.synthetic.main.fragment_tv_show.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentTvShow : BaseFragment() {

    lateinit var interactor: InteractorFragmentTv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interactor = InteractorFragmentTv(childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_tv_show, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setNavigationButton()
    }

    fun setItemSelectedNavigation(itemId: Int){
        if (itemId!=0) navigation_tv.selectedItemId = itemId
    }

    fun onSelectedCategoryPopular(){
        interactor.fragmentCategoryPopular()
    }

    private fun setNavigationButton() {
        removeShiftMode(navigation_tv)
        navigation_tv.setOnNavigationItemReselectedListener {}
        navigation_tv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular_tv -> {
                    interactor.fragmentCategoryPopular()
                }
                R.id.nav_top_rating_tv -> {
                    interactor.fragmentCategoryRating()
                }
                R.id.nav_air_tv -> {
                    interactor.fragmentCategoryTvAir()
                }
                R.id.nav_today_tv -> {
                    interactor.fragmentCategoryTvToday()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}