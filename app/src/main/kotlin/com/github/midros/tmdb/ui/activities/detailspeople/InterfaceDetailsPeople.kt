package com.github.midros.tmdb.ui.activities.detailspeople

import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.di.PerActivity
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfaceDetailsPeople {

    @PerActivity
    interface Interactor<V : View> : InterfaceInteractor<V>{
        fun getParams(): MutableMap<String, String>
        fun getDetailPeople(id:Int)
        fun getImagesTagged(id:Int)
        fun getImagesPeople(id:Int)
        fun getMovieCredits(id: Int)
        fun getTvCredits(id: Int)
        fun getExternalsIds(url:String)
    }

    interface View : InterfaceView{
        fun setDataPeople(data:PojoDetailsPerson)
        fun setDataImagesTagged(list: MutableList<ObjectImagesDetailsPersonTagged>)
        fun setDataImagesPeople(data: ObjectImagesPerson)
        fun setDataMovieCredits(list: MutableList<PojoResultsMovie>)
        fun setDataTvCredits(list: MutableList<PojoResultsTv>)
        fun setDataExternalIds(data: ObjectExternalIds)

        fun showLoadingData()
        fun hideLoadingData()
        fun failedData()

        fun showLoadingTagged()
        fun hideLoadingTagged()
        fun failedTagged()

        fun clearImagesPerson()
        fun clearMovieCredits()
        fun clearTvCredits()

    }

}