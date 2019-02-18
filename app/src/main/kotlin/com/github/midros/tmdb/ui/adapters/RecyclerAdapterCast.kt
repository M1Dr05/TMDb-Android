package com.github.midros.tmdb.ui.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectsDetailsCast
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.setImagePeopleUrl
import com.pawegio.kandroid.inflateLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_cast.view.*
import javax.inject.Inject


/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterCast @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterCast.ViewHolder>() {

    private var list: MutableList<ObjectsDetailsCast> = mutableListOf()
    private var onItemCastClickListener: OnItemCastClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(context.inflateLayout(R.layout.item_cast, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: ObjectsDetailsCast) {
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
        holder.itemView.linear_click.setOnClickListener {
            val profile = if (!data.profile_path.isNullOrEmpty()) data.profile_path else ""
            onItemCastClickListener!!.onItemCastClicked(data.id, data.name, profile,holder.itemView.img_cast)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ObjectsDetailsCast) = with(itemView) {
            name_cast.text = item.name
            character_cast.text = item.character
            img_cast.setImagePeopleUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.profile_path}"){}
        }
    }

    interface OnItemCastClickListener {
        fun onItemCastClicked(id: Int, name: String,image: String,v:View)
    }

    fun setOnItemCastClickListener(onItemCastClickListener: OnItemCastClickListener) {
        this.onItemCastClickListener = onItemCastClickListener
    }

}