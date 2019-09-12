package com.github.midros.tmdb.ui.adapters.adaptermovie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.midros.tmdb.data.model.PojoResultsMovie
import com.github.midros.tmdb.data.preference.DataSharePreference.getGenderMovie
import com.github.midros.tmdb.utils.ConstStrings
import com.github.midros.tmdb.utils.ConstFun.getReformatDate
import com.github.midros.tmdb.utils.ConstFun.setImageUrl
import kotlinx.android.synthetic.main.item_adapter.view.*

/**
 * Created by luis rafael on 16/02/19.
 */
class ViewHolderMovieDefault(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: PojoResultsMovie) = with(itemView) {
        title_adapter.text = item.title
        date_adapter.text = getReformatDate(item.release_date)
        rating_adapter.text = item.vote_average.toString()

        val genders = StringBuilder()

        (0 until item.genre_ids.size).asSequence()
                .forEach { if (it == item.genre_ids.size-1) genders.append(context.getGenderMovie(item.genre_ids[it].toString()))
                else genders.append(context.getGenderMovie(item.genre_ids[it].toString())).append(", ") }
        genero_adapter.text = genders.toString()

        img_poster.setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")

    }

}