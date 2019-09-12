package com.github.midros.tmdb.ui.adapters.adaptermovie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.PojoResultsMovie
import com.github.midros.tmdb.utils.ConstStrings
import kotlinx.android.synthetic.main.item_viewpager.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewPagerPopularMovie(private var context: Context, private var list: MutableList<PojoResultsMovie>) : PagerAdapter() {


    private var onItemClickListener: OnItemClickListener? = null

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == (`object` as RelativeLayout)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, container, false)

        val data = list[position]

        view.img_backdrop.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_W780}${data.backdrop_path}")
        view.img_poster.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${data.poster_path}")
        view.title_view.text = data.title
        view.vote_count.text = data.vote_count.toString()


        val backdrop = if (!data.backdrop_path.isNullOrEmpty()) data.backdrop_path else ""

        view.click_open_video.setOnClickListener {
            onItemClickListener!!.onItemCastClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
        }

        view.click_open_detail.setOnClickListener {
            onItemClickListener!!.onItemCastClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    fun ImageView.setImageUrl(url: String) {
        Glide.with(this).load(url)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(this)
    }

    interface OnItemClickListener {
        fun onItemCastClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }


}