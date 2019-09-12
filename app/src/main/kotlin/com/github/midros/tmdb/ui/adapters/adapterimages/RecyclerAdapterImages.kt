package com.github.midros.tmdb.ui.adapters.adapterimages

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.ObjectsDetailsImages
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter_poster.view.*
import kotlinx.android.synthetic.main.item_images.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterImages @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeImages = 1
    private val viewTypeImagesPerson = 2
    private var viewType:Int = 1

    private var list: MutableList<ObjectsDetailsImages> = mutableListOf()
    private var onItemImageClickListener: OnItemImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            viewTypeImagesPerson -> ViewHolderImagesPerson(context.inflateLayout(R.layout.item_adapter_poster,parent,false))
            else -> ViewHolderImages(context.inflateLayout(R.layout.item_images,parent,false))
        }

    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when(viewType) {
            2 -> viewTypeImagesPerson
            else -> viewTypeImages
        }
    }

    fun setType(type:Int){
        this.viewType = type
    }

    fun addItem(item: ObjectsDetailsImages) {
        list.add(item)

        notifyItemInserted(itemCount)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        when(holder.itemViewType){
            viewTypeImages-> (holder as ViewHolderImages).bind(data)
            viewTypeImagesPerson->{
                (holder as ViewHolderImagesPerson).bind(data)
                holder.itemView.click_adapter_horizontal.setOnClickListener {
                    onItemImageClickListener!!.onItemImageClicked(data.file_path,holder.itemView.img_poster)
                }
            }
        }
    }

    interface OnItemImageClickListener{
        fun onItemImageClicked(file_path:String,v:View)
    }

    fun setOnItemImageClickListener(onItemImageClickListener: OnItemImageClickListener){
        this.onItemImageClickListener = onItemImageClickListener
    }

}