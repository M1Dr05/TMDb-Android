package com.github.midros.tmdb.ui.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.pawegio.kandroid.inflateLayout
import com.pawegio.kandroid.longToast
import kotlinx.android.synthetic.main.item_adapter_multi.view.*
import javax.inject.Inject


/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterSearch @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolder>() {

    private var json: JsonArray = JsonArray()

    private var onItemSearchClickListener: OnItemSearchClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_adapter_multi, parent, false))

    override fun getItemCount(): Int = json.size()

    fun addItem(json: JsonArray) {
        this.json = json
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(json[position].asJsonObject)
        setOnItemSearchClickListener(holder, json[position].asJsonObject)
    }

    private fun setOnItemSearchClickListener(holder: ViewHolder, item: JsonObject) {
        holder.itemView.click_adapter_count3.setOnClickListener {
            when (item.get("media_type").asString) {
                ConstStrings.MOVIE -> {
                    if (!item.get("backdrop_path").isJsonNull) onItemSearchClickListener!!.onItemSearchClicked(item.get("id").asInt, item.get("title").asString, ConstStrings.MOVIE, item.get("backdrop_path").asString,holder.itemView.img_multi)
                    else context.longToast(item.get("title").asString)
                }
                ConstStrings.TV -> {
                    if (!item.get("backdrop_path").isJsonNull) onItemSearchClickListener!!.onItemSearchClicked(item.get("id").asInt, item.get("name").asString, ConstStrings.TV, item.get("backdrop_path").asString,holder.itemView.img_multi)
                    else context.longToast(item.get("name").asString)
                }
                ConstStrings.PERSON -> {
                    if (!item.get("profile_path").isJsonNull) onItemSearchClickListener!!.onItemSearchClicked(item.get("id").asInt, item.get("name").asString, ConstStrings.PERSON, item.get("profile_path").asString,holder.itemView.img_multi)
                    else context.longToast(item.get("name").asString)
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: JsonObject) = with(itemView) {
            when (item.get("media_type").asString) {
                ConstStrings.MOVIE -> if (!item.get("poster_path").isJsonNull) img_multi.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.get("poster_path").asString}")
                ConstStrings.TV -> if (!item.get("poster_path").isJsonNull) img_multi.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.get("poster_path").asString}")
                ConstStrings.PERSON -> if (!item.get("profile_path").isJsonNull) img_multi.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.get("profile_path").asString}")

            }
        }
    }

    interface OnItemSearchClickListener {
        fun onItemSearchClicked(id: Int, title: String, type: String, image: String,v:View)
    }

    fun setOnItemMovieClickListener(onItemSearchClickListener: OnItemSearchClickListener) {
        this.onItemSearchClickListener = onItemSearchClickListener
    }
}