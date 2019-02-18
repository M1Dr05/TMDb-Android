package com.github.midros.tmdb.data.model

import com.google.gson.JsonArray
import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
data class ObjectsSearch(
        var page: Int,
        var total_pages: Int,
        var results: JsonArray
) : Serializable