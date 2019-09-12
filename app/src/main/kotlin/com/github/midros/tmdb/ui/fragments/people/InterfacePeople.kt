package com.github.midros.tmdb.ui.fragments.people

import com.github.midros.tmdb.data.model.ObjectPerson
import com.github.midros.tmdb.ui.activities.base.InterfaceInteractor
import com.github.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 16/02/19.
 */
interface InterfacePeople {

    interface Interactor<V : View> : InterfaceInteractor<V>{
        fun getParams(): MutableMap<String, String>
        fun getPersons()
        fun getPage() : Int
    }

    interface View : InterfaceView{
        fun setAdapterRecycler()
        fun addItemRecycler(data:ObjectPerson)
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()
    }

}