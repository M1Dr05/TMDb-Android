package com.github.midros.tmdb.ui.adapters.adapterimages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectImagesDetailsPersonTagged
import com.github.midros.tmdb.utils.ConstStrings
import kotlinx.android.synthetic.main.item_pager_images.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewPagerImagesPeople @Inject constructor(private var context: Context) : PagerAdapter() {

    private var list: MutableList<ObjectImagesDetailsPersonTagged> = mutableListOf()

    override fun getCount(): Int = list.size

    fun addItems(list: MutableList<ObjectImagesDetailsPersonTagged>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == (`object` as LinearLayout)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pager_images, container, false)
        view.img_pager_images.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_W780}${list[position].file_path}")
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    fun ImageView.setImageUrl(url: String) {
        Glide.with(this).load(url)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(context.getDrawable(R.drawable.ic_placeholder)!!)
                        .error(context.getDrawable(R.drawable.ic_placeholder)!!))
                .into(this)
    }


}