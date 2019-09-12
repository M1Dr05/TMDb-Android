package com.github.midros.tmdb.ui.fragments.tvshows.category

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectTv
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.ui.adapters.adaptertv.RecyclerAdapterTvShows
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.OnScrollListenerUtils
import com.github.midros.tmdb.utils.ConstFun.animatedView
import com.daimajia.androidanimations.library.Techniques
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_category_tv_show.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentCategoryTv : BaseFragment(), InterfaceCategoryTv.ViewCategoryTv, RecyclerAdapterTvShows.OnItemTvClickListener {

    @Inject lateinit var interactor: InteractorCategoryTv<InterfaceCategoryTv.ViewCategoryTv>

    @Inject lateinit var adapterRecycler: RecyclerAdapterTvShows

    @field:Named(ConstStrings.DEFAULT)
    @Inject lateinit var lManager: GridLayoutManager

    fun newInstance(category: String): FragmentCategoryTv {
        val fragment = FragmentCategoryTv()
        val bundle = Bundle()
        bundle.putString(ConstStrings.CATEGORY, category)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
        interactor.onAttach(this)
    }

    override fun onDetach() {
        interactor.onDetach()
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_category_tv_show, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        interactor.setCategory(arguments!!.getString(ConstStrings.CATEGORY)!!)
        reloadConnectTvShow()
        setAdapterRecycler()
        interactor.getTvShow()
    }

    override fun addItemRecycler(data:ObjectTv){
        for (tv in data.results) adapterRecycler.addItem(tv)
        showList()
    }

    private fun reloadConnectTvShow() {
        connect_tv.setOnClickListener {
            interactor.getTvShow()
        }
    }

    override fun setAdapterRecycler() {
        list_tv_show.apply {
            setHasFixedSize(true)
            layoutManager = lManager
            recycledViewPool.clear()
            adapter = adapterRecycler
            addOnScrollListener(OnScrollListenerUtils({ interactor.getTvShow() }, lManager))
        }
        adapterRecycler.setType(1)
        adapterRecycler.notifyDataSetChanged()
        adapterRecycler.setOnItemTvClickListener(this)
    }

    override fun onItemTvClicked(id: Int, title: String, type: String, image: String) {
        getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    override fun showLoading() {
        connect_tv.hide()
        progress_tv.show()
        animatedView(Techniques.FadeIn, progress_tv, 100)
    }

    private fun showList(){
        hiddenLoading()
        runDelayedOnUiThread(200) {
            if (progress_tv != null) progress_tv.hide()
            if (list_tv_show != null){
                list_tv_show.show()
                if (interactor.getPage()==2) animatedView(Techniques.FadeIn,list_tv_show,1000)
            }
        }
    }

    override fun hiddenLoading() {
        if (progress_tv != null) animatedView(Techniques.SlideOutDown, progress_tv, 200)
    }

    override fun hiddenLoadingFailed() {
        if (progress_tv != null) progress_tv.hide()
        if (connect_tv != null) connect_tv.show()
    }
}