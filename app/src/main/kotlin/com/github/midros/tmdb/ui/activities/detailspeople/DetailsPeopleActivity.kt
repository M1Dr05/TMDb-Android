package com.github.midros.tmdb.ui.activities.detailspeople

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.Fade
import android.transition.TransitionSet
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.ui.activities.base.BaseActivity
import com.github.midros.tmdb.ui.adapters.adapterimages.RecyclerAdapterImages
import com.github.midros.tmdb.ui.adapters.adapterimages.ViewPagerImagesPeople
import com.github.midros.tmdb.ui.adapters.adaptermovie.RecyclerAdapterMovies
import com.github.midros.tmdb.ui.adapters.adaptertv.RecyclerAdapterTvShows
import com.github.midros.tmdb.utils.*
import com.github.midros.tmdb.utils.ConstStrings.Companion.ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.IMAGE
import com.github.midros.tmdb.utils.ConstStrings.Companion.PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.PERSON_ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.TITLE
import com.github.midros.tmdb.utils.ConstStrings.Companion.TYPE
import com.github.midros.tmdb.utils.ConstStrings.Companion.URL_TMDB
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.activity_detail_person.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 16/02/19.
 */
class DetailsPeopleActivity : BaseActivity(), InterfaceDetailsPeople.View, RecyclerAdapterImages.OnItemImageClickListener, RecyclerAdapterMovies.OnItemMovieClickListener, RecyclerAdapterTvShows.OnItemTvClickListener {


    private var fb:String?=null
    private var tw:String?=null
    private var it:String?=null
    private var tm:String?=null
    private var im:String?=null

    @Inject lateinit var pagerAdapter : ViewPagerImagesPeople
    @Inject lateinit var adapterImages: RecyclerAdapterImages
    @Inject lateinit var adapterMovieCredits : RecyclerAdapterMovies
    @Inject lateinit var adapterTvCredits: RecyclerAdapterTvShows

    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerImages: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerMovies: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerTv: GridLayoutManager

    @Inject lateinit var interactor: InteractorDetailsPeople<InterfaceDetailsPeople.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_person)
        getActivityComponent()!!.inject(this)
        interactor.onAttach(this)
        setActionBar()
        initializeViewPagerImages()
        supportPostponeEnterTransition()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val id = intent!!.getIntExtra(PERSON_ID,0)
        val name = intent!!.getStringExtra(PERSON)
        toolbar_layout.title = name
        getDetailsData(id)
        connect_details_person.setOnClickListener { getDetailsData(id) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details_person,menu)
        Handler().post { val view = findViewById<View>(R.id.nav_ids_person) ; view.setOnClickListener { showPopupMenuExternal(it) } }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){ R.id.nav_share_person-> openShareIn("$URL_TMDB$tm") }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupMenuExternal(view:View) = showPopupMenuExternal(view,fb,tw,it,tm!!,im)

    override fun onDestroy() {
        interactor.onDetach()
        clearDisposable()
        super.onDestroy()

    }

    private fun getDetailsData(id:Int){
        this.tm = "person/$id"
        interactor.getDetailPeople(id)
        interactor.getImagesTagged(id)
        interactor.getImagesPeople(id)
        interactor.getMovieCredits(id)
        interactor.getTvCredits(id)
        interactor.getExternalsIds("person/$id/external_ids")
    }

    private fun initializeViewPagerImages(){
        view_adapter_images_detail_people.apply {
            adapter = pagerAdapter
        }
        pageindicator_detail_person.attachTo(view_adapter_images_detail_people)
    }

    private fun setActionBar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { supportFinishAfterTransition() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            supportFinishAfterTransition()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun setDataExternalIds(data: ObjectExternalIds) {
        this.fb = data.facebook_id
        this.tw = data.twitter_id
        this.it = data.instagram_id
        this.im = data.imdb_id
    }

    override fun setDataPeople(data: PojoDetailsPerson) {
        hideLoadingData()
        toolbar_layout.title = data.name
        txt_born_person.text = if (!data.biography.isNullOrEmpty()) getReformatDate(data.birthday) else "-"
        if (!data.deathday.isNullOrEmpty()) { linear_decease_person.show(); txt_died_person.text= getReformatDate(data.deathday) } else linear_decease_person.hide()
        place_born_person.text = if (!data.place_of_birth.isNullOrEmpty()) data.place_of_birth else "-"

        biography_person.text = if (!data.biography.isNullOrEmpty()) data.biography else "${getString(R.string.no_biography)} ${data.name}"
        homepage_details_person.text = if (!data.homepage.isNullOrEmpty()) data.homepage else "-"

        if (homepage_details_person.text.toString() != "-") if (!data.homepage.isNullOrEmpty()) {
            homepage_details_person.paintFlags = homepage_details_person.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            homepage_details_person.setOnClickListener { openActivityBrowser(data.homepage) }
        }

        img_profile.setImagePeopleUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${data.profile_path}"){ supportStartPostponedEnterTransition() }
        img_profile.setOnClickListener { openActivityImage(data.profile_path,img_profile) }
    }

    override fun showLoadingData() {
        progress_details_person.show()
        data_details_person.hide()
        connect_details_person.hide()
    }

    override fun hideLoadingData() {
        progress_details_person.hide()
        data_details_person.show()
        connect_details_person.hide()
        animatedView(Techniques.FadeIn, data_details_person, 1000)
    }

    override fun failedData() {
        startPostponedEnterTransition()
        progress_details_person.hide()
        data_details_person.hide()
        connect_details_person.show()
    }


    override fun setDataImagesTagged(list: MutableList<ObjectImagesDetailsPersonTagged>) {
        val newList = mutableListOf<ObjectImagesDetailsPersonTagged>()
        for (i in 0 until list.size) if (list[i].aspect_ratio>1) newList.add(list[i])
        pagerAdapter.addItems(newList)
        pageindicator_detail_person.attachTo(view_adapter_images_detail_people)
        if (newList.isEmpty()) failedTagged() else hideLoadingTagged()
    }

    override fun showLoadingTagged() {
        view_adapter_images_detail_people.hide()
        progress_image_details_person.show()
        image_placeholder_person.hide()
    }

    override fun hideLoadingTagged() {
        view_adapter_images_detail_people.show()
        progress_image_details_person.hide()
        image_placeholder_person.hide()
    }

    override fun failedTagged() {
        view_adapter_images_detail_people.hide()
        progress_image_details_person.hide()
        image_placeholder_person.show()
    }

    override fun setDataImagesPeople(data: ObjectImagesPerson) {
        if (!data.profiles.isEmpty()) {
            adapterImages.setType(2)
            for (images in data.profiles) adapterImages.addItem(images)
            view_images_person.show()
            animatedView(Techniques.FadeIn, view_images_person, 1000)
        }
        list_images_person.apply {
            setHasFixedSize(false)
            layoutManager = lManagerImages
            isNestedScrollingEnabled = false
            adapter = adapterImages
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_images_person, 1000)
        adapterImages.notifyDataSetChanged()
        adapterImages.setOnItemImageClickListener(this)
        onClickAllImage(data)

    }

    override fun onItemImageClicked(file_path: String,v:View) = openActivityImage(file_path,v)

    private fun onClickAllImage(data: ObjectImagesPerson){
        btn_see_all_images_person.setOnClickListener { openImagesActivity(2,data) }
    }

    override fun setDataMovieCredits(list: MutableList<PojoResultsMovie>) {
        if (!list.isEmpty()) {
            list.sortBy { it.release_date }
            list.reverse()
            adapterMovieCredits.setType(2)
            for (movie in list) adapterMovieCredits.addItem(movie)
            view_movies_person.show()
            animatedView(Techniques.FadeIn, view_movies_person, 1000)
        }
        list_movies_person.apply {
            setHasFixedSize(false)
            layoutManager = lManagerMovies
            isNestedScrollingEnabled = false
            adapter = adapterMovieCredits
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_movies_person, 1000)
        adapterMovieCredits.notifyDataSetChanged()
        adapterMovieCredits.setOnItemMovieClickListener(this)
    }

    override fun onItemMovieClicked(id: Int, title: String, type: String, image: String) {
        setResult(id,title,type,image)
    }

    override fun setDataTvCredits(list: MutableList<PojoResultsTv>) {
        if (!list.isEmpty()){
            list.sortBy { it.first_air_date }
            list.reverse()
            adapterTvCredits.setType(2)
            for (tv in list) adapterTvCredits.addItem(tv)
            view_tv_person.show()
            animatedView(Techniques.FadeIn,view_tv_person,1000)
        }
        list_tv_person.apply {
            setHasFixedSize(true)
            layoutManager = lManagerTv
            isNestedScrollingEnabled = false
            adapter = adapterTvCredits
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight,list_tv_person,1000)
        adapterTvCredits.notifyDataSetChanged()
        adapterTvCredits.setOnItemTvClickListener(this)
    }

    override fun onItemTvClicked(id: Int, title: String, type: String, image: String) {
        setResult(id,title,type,image)
    }

    private fun setResult(id: Int, title: String, type: String, image: String){
        val intent = Intent()
        intent.putExtra(ID,id)
                .putExtra(TITLE,title)
                .putExtra(TYPE,type)
                .putExtra(IMAGE,image)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun clearImagesPerson() = adapterImages.clearList()
    override fun clearMovieCredits() = adapterMovieCredits.clearList()
    override fun clearTvCredits() = adapterTvCredits.clearList()

}