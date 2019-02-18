package com.github.midros.tmdb.ui.adapters.adapterimages

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectsDetailsImages
import com.github.midros.tmdb.utils.ConstStrings
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_pager_images.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewPagerImages @Inject constructor(private var context: Context) : PagerAdapter() {

    private var list: MutableList<ObjectsDetailsImages> = mutableListOf()

    fun addItems(list: MutableList<ObjectsDetailsImages>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = list.size

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
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(this)
    }


}
