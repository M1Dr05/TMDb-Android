package com.github.midros.tmdb.ui.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectDetailsTrailers
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.setImageUrl
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_trailers.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterTrailers @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterTrailers.ViewHolder>() {

    private var list: MutableList<ObjectDetailsTrailers> = mutableListOf()
    private var onItemTrailersClickListener: OnItemTrailersClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_trailers, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: ObjectDetailsTrailers) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.click_trailers.setOnClickListener {
            onItemTrailersClickListener!!.onItemTrailersClicked(data.key)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ObjectDetailsTrailers) = with(itemView) {
            txt_size_trailers.text = item.size
            img_trailer.setImageUrl("${ConstStrings.BASE_URL_IMAGE_YOUTUBE}${item.key}${ConstStrings.SIZE_IMG_YOUTUBE}")
        }
    }

    interface OnItemTrailersClickListener {
        fun onItemTrailersClicked(key_video: String)
    }

    fun setOnItemCastClickListener(onItemTrailersClickListener: OnItemTrailersClickListener) {
        this.onItemTrailersClickListener = onItemTrailersClickListener
    }
}