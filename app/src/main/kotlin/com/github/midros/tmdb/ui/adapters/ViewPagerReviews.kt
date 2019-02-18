package com.github.midros.tmdb.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectDetailsReviews
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_reviews.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewPagerReviews @Inject constructor(private var context: Context) : PagerAdapter() {

    private val list: MutableList<ObjectDetailsReviews> = mutableListOf()
    private var onClickReviewListener : OnClickReviewListener?=null

    override fun getCount(): Int = list.size

    fun addItems(list: MutableList<ObjectDetailsReviews>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList(){
        list.clear()
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == (`object` as LinearLayout)

    @SuppressLint("SetTextI18n")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //val view = context.inflateLayout(R.layout.item_reviews,container,false)
        val view = LayoutInflater.from(context).inflate(R.layout.item_reviews, container, false)

        view.title_review.text = "${context.getString(R.string.a_review_by)} ${list[position].author}"
        view.content_review.text = list[position].content
        view.click_review.setOnClickListener { onClickReviewListener!!.onItemReviewClicked(list[position].url) }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    interface OnClickReviewListener{
        fun onItemReviewClicked(url:String)
    }

    fun setOnclickReviewListener(onClickReviewListener: OnClickReviewListener){
        this.onClickReviewListener = onClickReviewListener
    }


}