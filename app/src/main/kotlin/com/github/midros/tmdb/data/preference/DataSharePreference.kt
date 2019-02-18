package com.github.midros.tmdb.data.preference

import android.content.Context
import androidx.core.content.edit
import com.github.midros.tmdb.utils.ConstStrings


/**
 * Created by luis rafael on 16/02/19.
 */
object DataSharePreference  {

    fun Context.setGenderMovie(id: String, gender: String) {
        getSharedPreferences(ConstStrings.MOVIE, Context.MODE_PRIVATE).edit { putString(id,gender) }
    }

    fun Context.getGenderMovie(id: String): String =
            getSharedPreferences(ConstStrings.MOVIE, Context.MODE_PRIVATE).getString(id, "")!!

    fun Context.setGenderTvShow(id: String, gender: String) =
            getSharedPreferences(ConstStrings.TV, Context.MODE_PRIVATE).edit { putString(id,gender) }

    fun Context.getGenderTvShow(id: String): String =
            getSharedPreferences(ConstStrings.TV, Context.MODE_PRIVATE).getString(id, "")!!

}