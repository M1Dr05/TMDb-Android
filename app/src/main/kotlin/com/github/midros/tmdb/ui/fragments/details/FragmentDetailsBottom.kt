package com.github.midros.tmdb.ui.fragments.details

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.ui.adapters.*
import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.utils.*
import com.daimajia.androidanimations.library.Techniques
import com.github.midros.tmdb.ui.adapters.adaptermovie.RecyclerAdapterMovies
import com.github.midros.tmdb.ui.adapters.adapterimages.RecyclerAdapterImages
import com.github.midros.tmdb.ui.adapters.adaptertv.RecyclerAdapterTvShows
import com.pawegio.kandroid.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_details_bottom.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentDetailsBottom : BaseFragment(), InterfaceFragmentDetails.View, RecyclerAdapterCast.OnItemCastClickListener, RecyclerAdapterTrailers.OnItemTrailersClickListener, RecyclerAdapterMovies.OnItemMovieClickListener, RecyclerAdapterTvShows.OnItemTvClickListener, ViewPagerReviews.OnClickReviewListener {

    @Inject lateinit var adapterCast: RecyclerAdapterCast
    @Inject lateinit var adapterImages: RecyclerAdapterImages
    @Inject lateinit var adapterTrailers: RecyclerAdapterTrailers
    @Inject lateinit var adapterSimilarMovie: RecyclerAdapterMovies
    @Inject lateinit var adapterSimilarTv: RecyclerAdapterTvShows
    @Inject lateinit var pagerAdapter : ViewPagerReviews

    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerCast: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerTrailers: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerImages: GridLayoutManager
    @field:Named(ConstStrings.COUNT3)
    @Inject lateinit var lManagerCount3: GridLayoutManager

    @Inject lateinit var interactor: InteractorFragmentDetails<InterfaceFragmentDetails.View>

    private var id: Int? = null
    private var type: String? = null

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
            inflater.inflate(R.layout.fragment_details_bottom, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickConnetFailed()
    }

    private fun onClickConnetFailed() {
        connect_details_bottom.setOnClickListener {
            setDetailsBottom(id!!, type!!)
        }
    }

    fun setDetailsBottom(id: Int, type: String) {
        this.id = id
        this.type = type
        when (type) {
            ConstStrings.MOVIE -> {
                interactor.getDetailsMovie(id)
                interactor.getCast("movie/$id/credits")
                interactor.getTrailers("movie/$id/videos")
                interactor.getImages("movie/$id/images")
                interactor.getReviews("movie/$id/reviews")
                interactor.getSimilarMovie(id)
            }
            ConstStrings.TV -> {
                interactor.getDetailsTv(id)
                interactor.getCast("tv/$id/credits")
                interactor.getTrailers("tv/$id/videos")
                interactor.getImages("tv/$id/images")
                interactor.getReviews("tv/$id/reviews")
                interactor.getSimilarTv(id)
            }
        }
    }

    override fun showLoading() {
        hiddenViews()
        progress_details_bottom.show()
    }

    override fun showDataDetails(){
        data_details_bottom.show()
        animatedView(Techniques.FadeIn, data_details_bottom, 1000)
    }

    override fun hiddenLoading() { progress_details_bottom.hide() }

    override fun hiddenFailed() {
        progress_details_bottom.hide()
        connect_details_bottom.show()
    }

    override fun hiddenViews() {
        data_details_bottom.hide()
        rating_progress_details_bottom.setValue(0.0f)
        view_movie_bottom.hide()
        view_tv_bottom.hide()
        view_cast_bottom.hide()
        view_images_bottom.hide()
        view_trailer_bottom.hide()
        view_reviews_bottom.hide()
        view_similar_bottom.hide()
        connect_details_bottom.hide()
    }

    @SuppressLint("SetTextI18n")
    override fun setDataMovies(data: PojoDetailsMovies) {
        showDataDetails()
        view_movie_bottom.show()
        title_details.text = data.title
        fecha_details_bottom.text = if (data.release_date != null) getReformatDate(data.release_date) else "-"
        voto_details_bottom.text = data.vote_count.toString()
        rating_progress_details_bottom.setValueAnimated(data.vote_average.toString().replace(".", "").toFloat(), 1500)
        status_details_bottom.text = if (!data.status.isEmpty()) data.status else "-"
        runtime_details_bottom.text = "${data.runtime}m"
        budget_details_bottom.text = getFormattedDollars(data.budget)
        revenue_details_bottom.text = getFormattedDollars(data.revenue)
        overview_details_bottom.text = if (!data.overview.isNullOrEmpty()) data.overview else "-"
        homepage_details_bottom.text = if (!data.homepage.isNullOrEmpty()) data.homepage else "-"

        if (homepage_details_bottom.text.toString() != "-") if (!data.homepage.isNullOrEmpty()) {
            homepage_details_bottom.paintFlags = homepage_details_bottom.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            homepage_details_bottom.setOnClickListener { context!!.openActivityBrowser(data.homepage) }
        }

        val genders = StringBuilder()
        (0 until data.genres.size).asSequence().forEach { if (it == data.genres.size-1) genders.append(data.genres[it].name) else genders.append(data.genres[it].name).append(", ") }
        genero_details_bottom.text = genders.toString()
    }

    override fun setDataTv(data: PojoDetailsTv) {
        showDataDetails()
        view_tv_bottom.show()
        title_details.text = data.name
        fecha_details_bottom.text = if (data.first_air_date != null) getReformatDate(data.first_air_date) else "-"
        voto_details_bottom.text = data.vote_count.toString()
        rating_progress_details_bottom.setValueAnimated(data.vote_average.toString().replace(".", "").toFloat(), 1500)
        status_details_bottom.text = if (!data.status.isEmpty()) data.status else "-"
        txt_seasons_bottom.text = data.number_of_seasons.toString()
        txt_episodes_bottom.text = data.number_of_episodes.toString()
        overview_details_bottom.text = if (!data.overview.isNullOrEmpty()) data.overview else "-"
        homepage_details_bottom.text = if (!data.homepage.isNullOrEmpty()) data.homepage else "-"

        if (homepage_details_bottom.text != "-") if (!data.homepage.isNullOrEmpty()) {
            homepage_details_bottom.paintFlags = homepage_details_bottom.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            homepage_details_bottom.setOnClickListener { context!!.openActivityBrowser(data.homepage) }
        }

        val runtime = StringBuilder()
        (0 until data.episode_run_time.size).asSequence().map { data.episode_run_time[it] }
                .forEach { runtime.append(it).append("m ") }
        runtime_details_bottom.text = runtime

        val genders = StringBuilder()
        (0 until data.genres.size).asSequence().forEach { if (it == data.genres.size-1) genders.append(data.genres[it].name) else genders.append(data.genres[it].name).append(", ") }
        genero_details_bottom.text = genders.toString()
    }

    override fun setReviews(list: MutableList<ObjectDetailsReviews>) {
        if (!list.isEmpty()){
            pagerAdapter.addItems(list)
            view_reviews_bottom.show()
            animatedView(Techniques.FadeIn,view_reviews_bottom,1000)
        }
        pager_reviews.apply {
            adapter = pagerAdapter
            clipToPadding = false
            setPadding(50,0,50,0)
        }
        pageindicator_review.attachTo(pager_reviews)
        pagerAdapter.setOnclickReviewListener(this)
    }

    override fun onItemReviewClicked(url: String) {
        getBaseActivity().openActivityBrowser(url)
    }

    override fun setCast(list: MutableList<ObjectsDetailsCast>) {
        if (!list.isEmpty()) {
            for (cast in list) adapterCast.addItem(cast)
            view_cast_bottom.show()
            animatedView(Techniques.FadeIn, view_cast_bottom, 1000)
        }
        list_cast_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerCast
            isNestedScrollingEnabled = false
            adapter = adapterCast
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_cast_bottom, 1000)
        adapterCast.notifyDataSetChanged()
        adapterCast.setOnItemCastClickListener(this)
    }

    override fun onItemCastClicked(id:Int,name: String,image:String,v: View) {
        if (image == "") showMessage(name)
        else getBaseActivity().openActivityPeople(id,name,v)
    }

    override fun setTrailers(list: MutableList<ObjectDetailsTrailers>) {
        if (!list.isEmpty()) {
            for (trailers in list) adapterTrailers.addItem(trailers)
            view_trailer_bottom.show()
            animatedView(Techniques.FadeIn, view_trailer_bottom, 1000)
        }
        list_trailers_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerTrailers
            isNestedScrollingEnabled = false
            adapter = adapterTrailers
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_trailers_bottom, 1000)
        adapterTrailers.notifyDataSetChanged()
        adapterTrailers.setOnItemCastClickListener(this)
    }

    override fun onItemTrailersClicked(key_video: String) {
        getBaseActivity().setCueVideo(key_video)
    }

    override fun setImages(data: ObjectImages) {
        if (!data.backdrops.isEmpty()) {
            adapterImages.setType(1)
            for (images in data.backdrops) adapterImages.addItem(images)
            view_images_bottom.show()
            animatedView(Techniques.FadeIn, view_images_bottom, 1000)
        }
        list_images_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerImages
            isNestedScrollingEnabled = false
            adapter = adapterImages
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_images_bottom, 1000)
        adapterImages.notifyDataSetChanged()
        onClickAllImage(data)
    }

    private fun onClickAllImage(data: ObjectImages) {
        btn_see_all_images.setOnClickListener {
            activity!!.openImagesActivity(1,data)
        }
    }

    override fun setDataSimilarMovie(list: MutableList<PojoResultsMovie>) {
        if (!list.isEmpty()) {
            adapterSimilarMovie.setType(3)
            for (movie in list) adapterSimilarMovie.addItem(movie)
            setAdapterRecycler(adapterSimilarMovie)
            adapterSimilarMovie.setOnItemMovieClickListener(this)
        }
    }

    override fun onItemMovieClicked(id: Int, title: String, type: String, image: String) {
        if (image == "") showMessage(title)
        else getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    override fun setDataSimilarTv(list: MutableList<PojoResultsTv>) {
        if (!list.isEmpty()) {
            adapterSimilarTv.setType(3)
            for (tv in list) adapterSimilarTv.addItem(tv)
            setAdapterRecycler(adapterSimilarTv)
            adapterSimilarTv.setOnItemTvClickListener(this)
        }
    }

    override fun onItemTvClicked(id: Int, title: String, type: String, image: String) {
        if (image == "") showMessage(title)
        else getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    private fun setAdapterRecycler(adapters: RecyclerView.Adapter<*>) {
        view_similar_bottom.show()
        list_similar_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerCount3
            isNestedScrollingEnabled = false
            adapter = adapters
            recycledViewPool.clear()
        }
        adapters.notifyDataSetChanged()
    }

    override fun clearListCast() = adapterCast.clearList()
    override fun clearListSimilarMovies() = adapterSimilarMovie.clearList()
    override fun clearListSimilarTv() = adapterSimilarTv.clearList()
    override fun clearListImages() = adapterImages.clearList()
    override fun clearListTrailers() = adapterTrailers.clearList()
    override fun clearListReviews() = pagerAdapter.clearList()

    override fun setDetailsTop(id: Int, name: String, type: String, image: String) {}
    override fun setCueVideo(key: String) {}
    override fun setImage() {}
    override fun setTrailer(data: ObjectDetailsTrailers) {}
    override fun setPausePlayer() {}
    override fun showMenuDetailsTop() {}
    override fun hiddenMenuDetailsTop() {}
    override fun showPlayer() {}
    override fun hiddenViewsPlayer() {}
    override fun showViewsPlayer() {}
    override fun getIdDetails(): Int = 0
    override fun setDataTrailer(data: ObjectTrailers) {}
    override fun setDataExternalIds(data: ObjectExternalIds) {}

}