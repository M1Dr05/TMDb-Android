package com.github.midros.tmdb.ui.adapters.adapterimages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.midros.tmdb.data.model.ObjectsDetailsImages
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import com.pawegio.kandroid.hide
import kotlinx.android.synthetic.main.item_adapter_poster.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewHolderImagesPerson(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    fun bind(item: ObjectsDetailsImages) = with(itemView){
        linear_text.hide()
        img_poster.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.file_path}")
    }

}