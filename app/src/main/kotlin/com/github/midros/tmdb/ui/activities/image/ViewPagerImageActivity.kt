package com.github.midros.tmdb.ui.activities.image

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import android.view.WindowManager
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.adapters.adapterimages.ViewPagerImages
import com.github.midros.tmdb.data.model.ObjectImages
import com.github.midros.tmdb.data.model.ObjectImagesPerson
import com.github.midros.tmdb.ui.activities.base.BaseActivity
import com.github.midros.tmdb.utils.ConstFun.pageTransformer
import kotlinx.android.synthetic.main.activity_view_image.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewPagerImageActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    private var dataImages : ObjectImages? = null
    private var dataImagesPoster : ObjectImagesPerson? = null
    private var type:Int = 1

    @Inject lateinit var adapterImages:ViewPagerImages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        getActivityComponent()!!.inject(this)
        getDataBundle()
    }

    private fun getDataBundle(){
        val bundle = intent.extras
        if (bundle!=null){
            type = bundle.getInt("type")
            if (type==1){
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                dataImages = bundle.getSerializable("data") as ObjectImages
            }else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                dataImagesPoster = bundle.getSerializable("data") as ObjectImagesPerson
            }
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initializePagerAdapter()
        setViews()
    }

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        txt_total_images.text = if (type==1) "/${dataImages!!.backdrops.size}" else "/${dataImagesPoster!!.profiles.size}"
        close_view_image.setOnClickListener { finish() }
    }

    private fun initializePagerAdapter() {
        if (type==1) adapterImages.addItems(dataImages!!.backdrops)
        else adapterImages.addItems(dataImagesPoster!!.profiles)


        view_adapter_images.apply {
            setPageTransformer(true, pageTransformer())
            adapter = adapterImages
        }
        adapterImages.notifyDataSetChanged()
        view_adapter_images.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        txt_image_actual.text = (position + 1).toString()
    }

}