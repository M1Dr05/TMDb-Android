package com.github.midros.tmdb.ui.activities.image

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import com.github.midros.tmdb.R
import com.github.midros.tmdb.ui.activities.base.BaseActivity
import com.github.midros.tmdb.utils.ConstStrings
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*
import java.lang.Exception

/**
 * Created by luis rafael on 16/02/19.
 */
class ImageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        setActionBar()
        val url = intent.getStringExtra(ConstStrings.IMAGE)
        supportPostponeEnterTransition()

        id_image.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_W780}$url")

    }

    private fun setActionBar(){
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
        toolbar.setNavigationOnClickListener { supportFinishAfterTransition() }
    }

    private fun ImageView.setImageUrl(url:String){
        Picasso.get().load(url)
                .into(this,object : Callback{
                    override fun onSuccess() { supportStartPostponedEnterTransition() }
                    override fun onError(e: Exception?) { supportStartPostponedEnterTransition() }
                })
    }

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }


}