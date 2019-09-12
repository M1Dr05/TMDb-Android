package com.github.midros.tmdb.ui.adapters.adaptertv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.midros.tmdb.data.model.PojoResultsTv
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import kotlinx.android.synthetic.main.item_adapter_poster.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewHolderTvHorizontal(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: PojoResultsTv) = with(itemView) {
        txt_title.text = item.name
        img_poster.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")
    }
}