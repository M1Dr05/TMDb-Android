package com.github.midros.tmdb.data.model

import java.io.Serializable

/**
 * Created by luis rafael on 16/02/19.
 */
data class ObjectExternalIds(
        var imdb_id:String,
        var facebook_id:String,
        var instagram_id:String,
        var twitter_id:String
) : Serializable