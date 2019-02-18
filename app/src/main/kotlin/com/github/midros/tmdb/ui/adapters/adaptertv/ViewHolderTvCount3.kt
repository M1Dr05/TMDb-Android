package com.github.midros.tmdb.ui.adapters.adaptertv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.midros.tmdb.data.model.PojoResultsTv
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.setImageUrl
import kotlinx.android.synthetic.main.item_adapter_multi.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewHolderTvCount3 (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: PojoResultsTv) = with(itemView) {
        img_multi.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")
    }
}