package com.github.midros.tmdb.ui.adapters.adapterimages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.midros.tmdb.data.model.ObjectsDetailsImages
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import kotlinx.android.synthetic.main.item_images.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewHolderImages(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: ObjectsDetailsImages) = with(itemView) {
        img_images.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_BACKDROP}" + item.file_path)
        voto_images.text = item.vote_count.toString()
    }
}