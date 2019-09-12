package com.github.midros.tmdb.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.viewpager.widget.ViewPager
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.github.midros.tmdb.BuildConfig
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.main.MainActivity
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_YOUTUBE
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_YOUTUBE
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.midros.tmdb.ui.activities.image.ViewPagerImageActivity
import com.github.midros.tmdb.ui.activities.detailspeople.DetailsPeopleActivity
import com.github.midros.tmdb.ui.activities.image.ImageActivity
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_FB
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_IT
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_PLAY
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_TW
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_FB
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_IMDB
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_IT
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_PLAY
import com.github.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_TW
import com.github.midros.tmdb.utils.ConstStrings.Companion.IMAGE
import com.github.midros.tmdb.utils.ConstStrings.Companion.PERSON
import com.github.midros.tmdb.utils.ConstStrings.Companion.PERSON_ID
import com.github.midros.tmdb.utils.ConstStrings.Companion.TAG
import com.github.midros.tmdb.utils.ConstStrings.Companion.URL_TMDB
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.pawegio.kandroid.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.lang.Exception
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by luis rafael on 16/02/19.
 */
object ConstFun {

    fun getApiKeyTmdb(): String = BuildConfig.API_KEY_TMDB

    private inline fun <reified V : View> View.find(@IdRes id: Int): V = findViewById(id)

    var version = "v${BuildConfig.VERSION_NAME}"

    fun getLocale(): String = Locale.getDefault().toString().replace("_", "-")

    fun getCountry(): String = Locale.getDefault().country

    fun getFormattedDollars(doublePayment: Long): String {
        val n = NumberFormat.getCurrencyInstance(Locale.US)
        return n.format(doublePayment)
    }

    fun e(t:Throwable){
        e(TAG, t.message.toString())
    }

    fun ImageView.setImageUrl(url: String) {
        Picasso.get().load(url)
                .placeholder(context.getDrawable(R.drawable.ic_placeholder)!!)
                .error(context.getDrawable(R.drawable.ic_placeholder)!!)
                .into(this)
    }

    fun ImageView.setImagePeopleUrl(url: String,callback:() -> Unit){
        Picasso.get().load(url)
                .placeholder(context.getDrawable(R.drawable.ic_placeholder_profile)!!)
                .error(context.getDrawable(R.drawable.ic_placeholder_profile)!!)
                .into(this,object : Callback {
                    override fun onSuccess() { callback() }
                    override fun onError(e: Exception?) { callback() }
                })
    }

    fun getReformatDate(dateInString: String?): String {

        return if (dateInString!=null){
            val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            try {
                val date = parser.parse(dateInString)
                formatter.format(date)
            } catch (e: ParseException) {
                "-"
            }
        }else "-"
    }

    fun Activity.animateActivity() = overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    inline fun <reified T : Any> Activity.openActivity(){
        startActivity<T>()
        animateActivity()
    }

    fun Activity.openActivityMain() {
        startActivity<MainActivity>()
        finish()
        animateActivity()
    }

    fun Activity.openActivityImage(url: String,v:View){
        val intent = Intent(this,ImageActivity::class.java)
        intent.putExtra(IMAGE,url)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,v, "image")
        startActivity(intent,options.toBundle())
        animateActivity()
    }

    fun Activity.openActivityPeople(id:Int,name:String,v:View){
        val intent = Intent(this,DetailsPeopleActivity::class.java)
        intent.putExtra(PERSON_ID,id).putExtra(PERSON,name)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,v,"image_people")
        startActivityForResult(intent,100,options.toBundle())
        animateActivity()
    }

    fun Activity.openImagesActivity(type:Int,data:Serializable){
        val bundle = Bundle()
        bundle.putInt("type",type)
        bundle.putSerializable("data", data)
        val intent = IntentFor<ViewPagerImageActivity>(this)
        intent.putExtras(bundle)
        startActivity(intent)
        animateActivity()
    }

    fun Context.openActivityBrowser(url: String){
        val builder = CustomTabsIntent.Builder()
        builder.setShowTitle(true)
        builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark))
        builder.setStartAnimations(this,R.anim.fade_in,R.anim.fade_out)
        builder.setExitAnimations(this,R.anim.fade_in,R.anim.fade_out)
        builder.addDefaultShareMenuItem()
        val customTabs = builder.build()
        customTabs.launchUrl(this,url.toUri())
    }

    fun Activity.openDrawOverPermissionSetting() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, "package:$packageName".toUri()).also {
            it.start(this)
            animateActivity()
        }
    }

    fun Context.openPlayStore(){
        try {
            Intent(Intent.ACTION_VIEW,"$BASE_URL_APP_PLAY$packageName".toUri()).start(this)
        }catch (e : ActivityNotFoundException){
            openActivityBrowser("$BASE_URL_BROWSER_PLAY$packageName")
        }
    }

    private fun Context.openTrailerYoutube(key: String) {
        try {
            val intentAppYoutube = Intent(Intent.ACTION_VIEW, "$BASE_URL_APP_YOUTUBE$key".toUri())
            startActivity(intentAppYoutube)
        } catch (e: ActivityNotFoundException) {
            openActivityBrowser("$BASE_URL_BROWSER_YOUTUBE$key")
        }
    }

    private fun Context.openFb(fb:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW,"$BASE_URL_APP_FB$BASE_URL_BROWSER_FB$fb".toUri())
            packageManager.getPackageInfo("com.facebook.katana", 0)
            startActivity(intent)
        }catch (e:ActivityNotFoundException){
            openActivityBrowser("$BASE_URL_BROWSER_FB$fb")
        }
    }

    private fun Context.openTw(tw:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW,"$BASE_URL_APP_TW$tw".toUri())
            packageManager.getPackageInfo("com.twitter.android", 0)
            startActivity(intent)
        }catch (e:ActivityNotFoundException){
            openActivityBrowser("$BASE_URL_BROWSER_TW$tw")
        }
    }

    private fun Context.openIt(it:String){
        try {
            val intent = Intent(Intent.ACTION_VIEW,"$BASE_URL_APP_IT$it".toUri())
            packageManager.getPackageInfo("com.instagram.android", 0)
            startActivity(intent)
        }catch (e:ActivityNotFoundException){
            openActivityBrowser("$BASE_URL_BROWSER_IT$it")
        }
    }

    fun Context.openShareIn(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, getString(R.string.share_in)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun fadeZoomTransitionActivity(activity: Activity, view: View) {
        val animation = activity.loadAnimation(R.anim.fade_in)
        animation.duration = 1000
        animation.animListener {
            onAnimationEnd {
                view.startAnimation(activity.loadAnimation(R.anim.zoom_repeat))
            }
        }
        view.startAnimation(animation)
    }

    fun animatedView(t: Techniques, v: View, l: Long) {
        YoYo.with(t).duration(l).pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT).playOn(v)
    }

    @SuppressLint("RestrictedApi")
    fun removeShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        menuView.buildMenuView()
    }

    fun pageTransformer(): ViewPager.PageTransformer = ViewPager.PageTransformer { page, position ->
        page.translationX = if (position < 0.0f) 0.0f else (-page.width).toFloat() * position
    }

    fun parallaxPageTransformer(vararg v : Int) : ViewPager.PageTransformer = ViewPager.PageTransformer { page, position ->
        for (i in 0 until v.size){
            val view = page.findViewById<View>(v[i])
            if (view!=null) ViewCompat.setTranslationX(view,(page.width/1.5 * position).toFloat())
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun Context.getUrlYoutube(key: String, setUrl:(url:String?)-> Unit){
        try {
            object : YouTubeExtractor(this){
                override fun onExtractionComplete(p0: SparseArray<YtFile>?, p1: VideoMeta?) {
                    setUrl(p0?.get(22)?.url)
                }
            }.extract(BASE_URL_BROWSER_YOUTUBE+key,false,false)
        }catch (t:Throwable){
            e(t)
        }
    }

    fun Context.showPopupMenu(view: View,key:String,name:String){
        popupMenu {
            style = R.style.Widget_MPM_Menu_Dark_Custom
            dropdownGravity = Gravity.BOTTOM
            section {
                item {
                    label = getString(R.string.open_with_youtube)
                    icon = R.drawable.ic_youtube
                    callback = { openTrailerYoutube(key) }
                }
                item {
                    label = getString(R.string.share_in)
                    icon = R.drawable.ic_share_white_24dp
                    callback = { openShareIn("$name - $BASE_URL_BROWSER_YOUTUBE$key") }
                }
            }
        }.show(this,view)
    }

    fun Context.showPopupMenuExternal(v: View,fb:String?,tw:String?,it:String?,tm:String,im:String?){
        popupMenu {
            style = R.style.Widget_MPM_Menu_Dark_Custom
            dropdownGravity = Gravity.BOTTOM
            if (!fb.isNullOrEmpty() || !tw.isNullOrEmpty() || !it.isNullOrEmpty())
                section {
                    title = getString(R.string.social_media)
                    if (!fb.isNullOrEmpty()) item { label = "Facebook" ; icon = R.drawable.ic_facebook; callback = { openFb(fb) } }
                    if (!tw.isNullOrEmpty()) item { label = "Twitter"; icon = R.drawable.ic_twitter; callback = { openTw(tw) } }
                    if (!it.isNullOrEmpty()) item { label = "Instagram"; icon = R.drawable.ic_instagram; callback = { openIt(it) } }
                }
            section {
                title = getString(R.string.discover)
                item { label = "TMDb"; icon = R.drawable.ic_tmdb; callback = { openActivityBrowser("$URL_TMDB$tm") } }
                if (!im.isNullOrEmpty()) item { label = "IMDb"; icon = R.drawable.ic_imdb; callback = { openActivityBrowser("$BASE_URL_BROWSER_IMDB$im") } }
            }
        }.show(this,v)
    }

    @SuppressLint("SetTextI18n")
    fun Context.showDialogAbout(){
        val v = inflateLayout(R.layout.activity_about,null,false)
        v.find<TextView>(R.id.txt_version).text = version
        v.find<LinearLayout>(R.id.btn_web_page).setOnClickListener { openActivityBrowser(getString(R.string.link_github)) }
        v.find<TextView>(R.id.txt_web_page).paintFlags = Paint.UNDERLINE_TEXT_FLAG
        v.find<LinearLayout>(R.id.btn_policy).setOnClickListener { openActivityBrowser(getString(R.string.link_tmdb)) }
        v.find<TextView>(R.id.txt_policy).paintFlags = Paint.UNDERLINE_TEXT_FLAG

        alert { customView(v) ; cancellable(true) }.builder.create().show()
    }

}
