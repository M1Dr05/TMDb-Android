package com.github.midros.tmdb.ui.adapters.adaptertv

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.PojoResultsTv
import com.github.midros.tmdb.utils.*
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter.view.*
import kotlinx.android.synthetic.main.item_adapter_multi.view.*
import kotlinx.android.synthetic.main.item_adapter_poster.view.*
import javax.inject.Inject


/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterTvShows @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeTvDefault = 1
    private val viewTypeTvHorizontal = 2
    private val viewTypeTvCount3 = 3
    private var viewType:Int=1

    private var list: MutableList<PojoResultsTv> = mutableListOf()
    private var onItemTvClickListener: OnItemTvClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            viewTypeTvHorizontal-> ViewHolderTvHorizontal(context.inflateLayout(R.layout.item_adapter_poster, parent, false))
            viewTypeTvCount3-> ViewHolderTvCount3(context.inflateLayout(R.layout.item_adapter_multi, parent, false))
            else -> ViewHolderTvDefault(context.inflateLayout(R.layout.item_adapter, parent, false))
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            2 -> viewTypeTvHorizontal
            3 -> viewTypeTvCount3
            else -> viewTypeTvDefault
        }
    }

    fun setType(type: Int){
        this.viewType = type
    }

    fun addItem(item: PojoResultsTv) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        val backdrop = if (!data.backdrop_path.isNullOrEmpty()) data.backdrop_path else ""

        when(holder.itemViewType){
            viewTypeTvDefault->{
                (holder as ViewHolderTvDefault).bind(data)
                holder.itemView.click_adapter_default.setOnClickListener {
                    onItemTvClickListener!!.onItemTvClicked(data.id,data.name,ConstStrings.TV,backdrop)
                }
            }
            viewTypeTvHorizontal->{
                (holder as ViewHolderTvHorizontal).bind(data)
                holder.itemView.click_adapter_horizontal.setOnClickListener {
                    onItemTvClickListener!!.onItemTvClicked(data.id,data.name,ConstStrings.TV,backdrop)
                }
            }
            viewTypeTvCount3->{
                (holder as ViewHolderTvCount3).bind(data)
                holder.itemView.click_adapter_count3.setOnClickListener {
                    onItemTvClickListener!!.onItemTvClicked(data.id,data.name,ConstStrings.TV,backdrop)
                }
            }
        }

    }

    interface OnItemTvClickListener {
        fun onItemTvClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemTvClickListener(onItemTvClickListener: OnItemTvClickListener) {
        this.onItemTvClickListener = onItemTvClickListener
    }
}