package com.github.midros.tmdb.ui.fragments.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.*
import com.github.midros.tmdb.ui.activities.base.BaseFragment
import com.github.midros.tmdb.utils.ConstFun.showPopupMenuExternal
import com.github.midros.tmdb.utils.ConstFun.getUrlYoutube
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import com.github.midros.tmdb.utils.ConstFun.showPopupMenu
import com.github.midros.tmdb.utils.ConstFun.openDrawOverPermissionSetting
import com.github.midros.tmdb.utils.ConstStrings
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_details_top.*
import org.song.videoplayer.*
import org.song.videoplayer.floatwindow.FloatParams
import org.song.videoplayer.media.AndroidMedia
import org.song.videoplayer.rederview.IRenderView
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class FragmentDetailsTop : BaseFragment(), InterfaceFragmentDetails.View, PlayListener {


    private var idDetails = 0
    private var image: String? = null
    private var key: String? = null
    private var name: String? = null
    private var title: String? = null
    private var checkImage = false
    private var fb:String?=null
    private var tw:String?=null
    private var it:String?=null
    private var tm:String?=null
    private var im:String?=null
    private var urlVideo : String?=null

    @Inject lateinit var interactor: InteractorFragmentDetails<InterfaceFragmentDetails.View>

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
            inflater.inflate(R.layout.fragment_details_top, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    @SuppressLint("RestrictedApi")
    private fun initView() {
        click_keyboard_top.setOnClickListener { getBaseActivity().setMinimizeDraggable() }
        click_menu_top.setOnClickListener { showPopupMenu() }
        click_menu_external_top.setOnClickListener { showPopupMenuExternal() }
        click_window_float_top.setOnClickListener { showWindowFloat() }
        click_trailers_top.setOnClickListener { if (urlVideo!=null) { video_player.play(); showPlayer() } }
    }

    private fun showWindowFloat(){
        if (video_player.currentMode == IVideoPlayer.MODE_WINDOW_FLOAT_ACT) return
        var params = video_player.floatParams
        if (params==null){
            params = FloatParams()
            params.x = 0
            params.y = 0
            params.w = resources.displayMetrics.widthPixels * 1 / 2
            params.h = params.w * 9 / 16
            params.round = 10
            params.fade = 1f
            params.canMove = true
            params.canCross = true
        }
        params.systemFloat = true
        if (video_player.isWindowFloatMode) video_player.quitWindowFloat()
        else if (!video_player.enterWindowFloat(params)) {
            if (video_player.isPlaying) video_player.pause()
            getBaseActivity().openDrawOverPermissionSetting()
        }
        if (video_player.isSystemFloatMode) getBaseActivity().moveActivityToBack()
    }

    private fun showPopupMenuExternal() = context!!.showPopupMenuExternal(click_menu_external_top,fb,tw,it,tm!!,im)

    private fun showPopupMenu() = context!!.showPopupMenu(click_menu_top,key!!,name!!)

    override fun getIdDetails(): Int = idDetails

    override fun setDetailsTop(id: Int, name: String, type: String, image: String) {
        this.idDetails = id
        this.image = image
        this.title = name
        setPausePlayer()
        setTmdb(id,type)
        setTypeTrailer(id, type)
    }

    private fun setTmdb(id: Int,type: String){
        when (type) {
            ConstStrings.MOVIE -> tm = "movie/$id"
            ConstStrings.TV -> tm = "tv/$id"
        }
    }

    private fun setTypeTrailer(id: Int, type: String) {
        when (type) {
            ConstStrings.MOVIE -> {
                interactor.getTrailers("movie/$id/videos")
                interactor.getExternalIds("movie/$id/external_ids")
            }
            ConstStrings.TV -> {
                interactor.getTrailers("tv/$id/videos")
                interactor.getExternalIds("tv/$id/external_ids")
            }
        }
    }

    override fun setDataExternalIds(data: ObjectExternalIds) {
        this.fb = data.facebook_id
        this.tw = data.twitter_id
        this.it = data.instagram_id
        this.im = data.imdb_id
    }

    override fun setDataTrailer(data: ObjectTrailers){
        if (data.results.isNotEmpty()) {
            if (!data.results[0].key.isNullOrEmpty()) setTrailer(data.results[0])
            else setImage()
        }
        else setImage()
    }

    override fun setCueVideo(key: String) {
        context!!.getUrlYoutube(key){
            if (it!=null){
                urlVideo = it
                showPlayer()
                video_player.isWindowGesture = true
                video_player.release()
                video_player.setAspectRatio(IRenderView.AR_MATCH_PARENT)
                video_player.setDecodeMedia(AndroidMedia::class.java)
                video_player.setUp(it,title)
                video_player.play()
            }else {
                setImage()
            }
        }
    }

    override fun showLoading() {
        progressDetails(true)
        fragmentYoutube(false)
        clickTrailers(false)
        imageTrailers(false)
        failedDetails(false)
    }

    override fun hiddenLoading() {
        progressDetails(false)
        clickTrailers(true)
        imageTrailers(true)
        fragmentYoutube(true)
    }

    override fun hiddenFailed() {
        progressDetails(false)
        failedDetails(true)
    }

    override fun setImage() {
        urlVideo = null
        checkImage = true
        clickTrailers(false)
        fragmentYoutube(false)
        progressDetails(false)
        clickMenuHideShow(false)
        imageTrailers(true)
        setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_BACKDROP}$image")
    }

    override fun setTrailer(data: ObjectDetailsTrailers) {
        key = data.key
        name = data.name
        context!!.getUrlYoutube(data.key){
            hiddenLoading()
            showViewsPlayer()
            checkImage = false
            clickMenuHideShow(true)
            setImageUrl("${ConstStrings.BASE_URL_IMAGE_YOUTUBE}${data.key}${ConstStrings.SIZE_IMG_YOUTUBE}")
            video_player.isWindowGesture = true
            video_player.release()
            video_player.setAspectRatio(IRenderView.AR_MATCH_PARENT)
            video_player.setDecodeMedia(AndroidMedia::class.java)
            if (it!=null) {
                urlVideo = it
                video_player.setUp(it,title)
            } else setImage()
            video_player.setPlayListener(this)
        }

    }

    private fun setImageUrl(url: String) {
        img_trailer_top.setImageUrl(url)
    }

    override fun setPausePlayer() {
        video_player.pause()
    }

    override fun showMenuDetailsTop() {
        if (!video_player.isPlaying){
            clickKeyboardHideShow(true)
            clickMenuHideShow(!checkImage)
        }
    }

    override fun hiddenMenuDetailsTop() {
        clickKeyboardHideShow(false)
        clickMenuHideShow(false)
    }

    override fun showPlayer() {
        clickTrailers(false)
        imageTrailers(false)
        fragmentYoutube(true)
    }

    override fun hiddenViewsPlayer() {
        clickKeyboardHideShow(false)
        clickMenuHideShow(false)
    }

    override fun showViewsPlayer() {
        clickKeyboardHideShow(true)
        clickMenuHideShow(!checkImage)
    }

    override fun onMode(mode: Int) {
        if (mode==QSVideoView.MODE_WINDOW_NORMAL) getWindowFlagStable()
    }

    override fun onEvent(what: Int, vararg extra: Int?) {
        when(what){
            DemoQSVideoView.EVENT_CONTROL_VIEW -> if (video_player.isPlaying) hiddenViewsPlayer()
            DemoQSVideoView.EVENT_CLICK_VIEW -> {
                showViewsPlayer()
                if (video_player.isWindowFloatMode) if (extra[0] == R.id.help_float_close ) getBaseActivity().finish()
            }
            DemoQSVideoView.EVENT_COMPLETION -> if (getBaseActivity().isMaximized()) showViewsPlayer()
        }
    }

    override fun onDestroyPlayer() {
        if (video_player.isSystemFloatMode) video_player.quitWindowFloat()
    }

    private fun clickKeyboardHideShow(b: Boolean) {
        if (b) click_keyboard_top.show() else click_keyboard_top.hide()
    }

    private fun clickMenuHideShow(b: Boolean) {
        if (b) click_menu_top.show() else click_menu_top.hide()
        if (b) click_menu_external_top.show() else click_menu_external_top.hide()
        if (b) click_window_float_top.show() else click_window_float_top.hide()
    }

    private fun clickTrailers(b: Boolean) {
        if (b) click_trailers_top.show() else click_trailers_top.hide()
    }

    private fun progressDetails(b: Boolean) {
        if (b) progress_details_top.show() else progress_details_top.hide()
    }

    private fun imageTrailers(b: Boolean) {
        if (b) img_trailer_top.show() else img_trailer_top.hide()
    }

    private fun fragmentYoutube(b: Boolean) {
        if (b) video_player.show() else video_player.hide()
    }

    private fun failedDetails(b: Boolean) {
        if (b) failed_details_top.show() else failed_details_top.hide()
    }


    override fun onStatus(status: Int) {}
    override fun showDataDetails() {}
    override fun hiddenViews() {}
    override fun setDataMovies(data: PojoDetailsMovies) {}
    override fun setDataTv(data: PojoDetailsTv) {}
    override fun setCast(list: MutableList<ObjectsDetailsCast>) {}
    override fun setTrailers(list: MutableList<ObjectDetailsTrailers>) {}
    override fun setImages(data: ObjectImages) {}
    override fun setDataSimilarMovie(list: MutableList<PojoResultsMovie>) {}
    override fun setDataSimilarTv(list: MutableList<PojoResultsTv>) {}
    override fun clearListCast() {}
    override fun clearListSimilarMovies() {}
    override fun clearListSimilarTv() {}
    override fun clearListImages() {}
    override fun clearListTrailers() {}
    override fun clearListReviews() {}
    override fun setReviews(list: MutableList<ObjectDetailsReviews>) {}

}
