package com.github.midros.tmdb.ui.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.github.midros.tmdb.R
import com.github.midros.tmdb.data.model.PojoResultsPerson
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.setImagePeopleUrl
import com.pawegio.kandroid.inflateLayout
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_adapter_people.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 16/02/19.
 */
class RecyclerAdapterPeople @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterPeople.ViewHolder>()  {

    private var list:MutableList<PojoResultsPerson> = mutableListOf()
    private var onItemPeopleClickListener : OnItemPeopleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_adapter_people,parent,false))

    override fun getItemCount(): Int = list.size

    fun addItem(item:PojoResultsPerson){
        list.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.click_adapter_people.setOnClickListener {
            val profile = if (!data.profile_path.isNullOrEmpty()) data.profile_path else ""
            onItemPeopleClickListener!!.onItemPeopleClicked(data.id,data.name,profile,holder.itemView.img_people)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: PojoResultsPerson) = with(itemView){
            name_people.text = item.name
            img_people.setImagePeopleUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.profile_path}"){}
        }
    }

    interface OnItemPeopleClickListener{
        fun onItemPeopleClicked(id:Int,name: String,image:String,v: View)
    }

    fun setOnItemPeopleClickListener(onItemPeopleClickListener: OnItemPeopleClickListener){
        this.onItemPeopleClickListener = onItemPeopleClickListener
    }

}