package com.github.midros.tmdb.ui.fragments.people

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.androidanimations.library.Techniques
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectPerson
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.ui.adapters.RecyclerAdapterPeople
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.OnScrollListenerUtils
import com.github.midros.tmdb.utils.ConstFun.animatedView
import com.github.midros.tmdb.utils.ConstFun.openActivityPeople
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentPeople : BaseFragment() , InterfacePeople.View, RecyclerAdapterPeople.OnItemPeopleClickListener {

    @Inject lateinit var interactor:InteractorFragmentPeople<InterfacePeople.View>

    @Inject lateinit var adapterRecycler : RecyclerAdapterPeople

    @field:Named(ConstStrings.DEFAULT)
    @Inject lateinit var lManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        getActivityComponent()!!.inject(this)
        interactor.onAttach(this)
    }

    override fun onDetach() {
        interactor.onDetach()
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_people, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reloadConnectPeople()
        setAdapterRecycler()
        interactor.getPersons()
    }

    override fun addItemRecycler(data: ObjectPerson) {
        for (people in data.results) adapterRecycler.addItem(people)
        showList()
    }

    fun reloadConnectPeople(){
        connect_people.setOnClickListener {
            interactor.getPersons()
        }
    }

    override fun setAdapterRecycler() {
        list_people.apply {
            setHasFixedSize(true)
            layoutManager = lManager
            recycledViewPool.clear()
            adapter = adapterRecycler
            addOnScrollListener(OnScrollListenerUtils({ interactor.getPersons() },lManager))
        }
        adapterRecycler.notifyDataSetChanged()
        adapterRecycler.setOnItemPeopleClickListener(this)
    }

    override fun onItemPeopleClicked(id:Int,name: String,image:String,v: View) {
        if (image == "") showMessage(name)
        else getBaseActivity().openActivityPeople(id,name,v)
    }

    override fun showLoading() {
        connect_people.hide()
        progress_people.show()
        animatedView(Techniques.FadeIn,progress_people,100)
    }

    private fun showList(){
        runDelayedOnUiThread(200){
            if (progress_people != null) progress_people.hide()
            if (list_people != null) {
                list_people.show()
                if (interactor.getPage()==2) animatedView(Techniques.FadeIn,list_people,1000)
            }
        }
    }

    override fun hiddenLoading() {
        if (progress_people!=null) animatedView(Techniques.SlideOutDown,progress_people,200)
    }

    override fun hiddenLoadingFailed() {
        if (progress_people!=null) progress_people.hide()
        if (connect_people!=null) connect_people.show()
    }


}