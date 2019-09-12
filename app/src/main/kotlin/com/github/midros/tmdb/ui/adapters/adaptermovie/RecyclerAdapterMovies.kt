package com.github.midros.tmdb.ui.adapters.adaptermovie

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.PojoResultsMovie
import com.github.midros.tmdb.utils.*
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter.view.*
import kotlinx.android.synthetic.main.item_adapter_multi.view.*
import kotlinx.android.synthetic.main.item_adapter_poster.view.*
import javax.inject.Inject


/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterMovies @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeMovieDefault = 1
    private val viewTypeMovieHorizontal = 2
    private val viewTypeMovieCount3 = 3
    private var viewType:Int=1

    private var list: MutableList<PojoResultsMovie> = mutableListOf()
    private var onItemMovieClickListener: OnItemMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            viewTypeMovieHorizontal-> ViewHolderMovieHorizontal(context.inflateLayout(R.layout.item_adapter_poster, parent, false))
            viewTypeMovieCount3-> ViewHolderMovieCount3(context.inflateLayout(R.layout.item_adapter_multi, parent, false))
            else -> ViewHolderMovieDefault(context.inflateLayout(R.layout.item_adapter, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int = when (viewType) {
            2 -> viewTypeMovieHorizontal
            3 -> viewTypeMovieCount3
            else -> viewTypeMovieDefault
        }


    override fun getItemCount(): Int = list.size

    fun setType(type:Int){
        this.viewType = type
    }

    fun addItem(item: PojoResultsMovie) {
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
            viewTypeMovieDefault->{
                (holder as ViewHolderMovieDefault).bind(data)
                holder.itemView.click_adapter_default.setOnClickListener {
                    onItemMovieClickListener!!.onItemMovieClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
                }
            }
            viewTypeMovieHorizontal->{
                (holder as ViewHolderMovieHorizontal).bind(data)
                holder.itemView.click_adapter_horizontal.setOnClickListener {
                    onItemMovieClickListener!!.onItemMovieClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
                }
            }
            viewTypeMovieCount3->{
                (holder as ViewHolderMovieCount3).bind(data)
                holder.itemView.click_adapter_count3.setOnClickListener {
                    onItemMovieClickListener!!.onItemMovieClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
                }
            }
        }
    }

    interface OnItemMovieClickListener {
        fun onItemMovieClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemMovieClickListener(onItemMovieClickListener: OnItemMovieClickListener) {
        this.onItemMovieClickListener = onItemMovieClickListener
    }

}