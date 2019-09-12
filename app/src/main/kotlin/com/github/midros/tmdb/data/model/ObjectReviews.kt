package com.github.midros.tmdb.data.model

import java.io.Serializable

/**
 * Created by luis rafael on 16/02/19.
 */
data class ObjectReviews(
        var id:Int,
        var page:Int,
        var total_pages:Int,
        var results:MutableList<ObjectDetailsReviews>
) : Serializable

data class ObjectDetailsReviews(
        var author:String,
        var content:String,
        var id:String,
        var url:String
) : Serializable

