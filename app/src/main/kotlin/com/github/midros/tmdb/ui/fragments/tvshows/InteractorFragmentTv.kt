package com.github.midros.tmdb.ui.fragments.tvshows

import androidx.fragment.app.FragmentManager
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.fragments.tvshows.category.FragmentCategoryTv
import com.github.midros.tmdb.utils.ConstStrings
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class InteractorFragmentTv (private var childManager: FragmentManager) : InterfaceFragmentTv {


    override fun fragmentCategoryPopular() {
        setFragmentCategory(ConstStrings.POPULAR_TV)
    }

    override fun fragmentCategoryRating() {
        setFragmentCategory(ConstStrings.TOP_RATED_TV)
    }

    override fun fragmentCategoryTvAir() {
        setFragmentCategory(ConstStrings.AIR_TV)
    }

    override fun fragmentCategoryTvToday() {
        setFragmentCategory(ConstStrings.TODAY_TV)
    }

    private fun setFragmentCategory(category: String) {
        val fragmentNow = childManager.findFragmentByTag("tv")
        val trans = childManager.beginTransaction()
        if (fragmentNow != null) trans.remove(fragmentNow)
        trans.disallowAddToBackStack()
        trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        trans.replace(R.id.frame_principal_tv, FragmentCategoryTv().newInstance(category), "tv")
        trans.commit()
    }
}