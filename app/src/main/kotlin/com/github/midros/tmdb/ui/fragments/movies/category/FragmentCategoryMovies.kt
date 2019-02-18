package com.github.midros.tmdb.ui.fragments.movies.category

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectMovies
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.ui.adapters.adaptermovie.RecyclerAdapterMovies
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.OnScrollListenerUtils
import com.github.midros.tmdb.utils.animatedView
import com.daimajia.androidanimations.library.Techniques
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_category_movies.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentCategoryMovies : BaseFragment(), InterfaceCategoryMovies.ViewCategoryMovies, RecyclerAdapterMovies.OnItemMovieClickListener {

    @Inject lateinit var interactor: InteractorCategoryMovies<InterfaceCategoryMovies.ViewCategoryMovies>

    @Inject lateinit var adapterRecycler: RecyclerAdapterMovies

    @field:Named(ConstStrings.DEFAULT)
    @Inject lateinit var lManager: GridLayoutManager

    fun newInstance(category: String): FragmentCategoryMovies {
        val fragment = FragmentCategoryMovies()
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
            inflater.inflate(R.layout.fragment_category_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        interactor.setCategory(arguments!!.getString(ConstStrings.CATEGORY)!!)
        reloadConnectMovies()
        setAdapterRecycler()
        interactor.getMovies()
    }

    override fun addItemRecycler(data:ObjectMovies){
        for (movie in data.results) adapterRecycler.addItem(movie)
        showList()
    }

    private fun reloadConnectMovies() {
        connect_movies.setOnClickListener {
            interactor.getMovies()
        }
    }

    override fun setAdapterRecycler() {
        list_movies.apply {
            setHasFixedSize(true)
            layoutManager = lManager
            recycledViewPool.clear()
            adapter = adapterRecycler
            addOnScrollListener(OnScrollListenerUtils({ interactor.getMovies() }, lManager))
        }
        adapterRecycler.setType(1)
        adapterRecycler.notifyDataSetChanged()
        adapterRecycler.setOnItemMovieClickListener(this)
    }

    override fun onItemMovieClicked(id: Int, title: String, type: String, image: String) {
        getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    override fun showLoading() {
        connect_movies.hide()
        progress_movies.show()
        animatedView(Techniques.FadeIn, progress_movies, 100)
    }

    private fun showList(){
        runDelayedOnUiThread(200) {
            if (progress_movies != null) progress_movies.hide()
            if (list_movies != null) {
                list_movies.show()
                if (interactor.getPage()==2) animatedView(Techniques.FadeIn,list_movies,1000)
            }
        }
    }

    override fun hiddenLoading() {
        if (progress_movies != null) animatedView(Techniques.SlideOutDown, progress_movies, 200)
    }

    override fun hiddenLoadingFailed() {
        if (progress_movies != null) progress_movies.hide()
        if (connect_movies != null) connect_movies.show()
    }

}