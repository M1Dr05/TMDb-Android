package com.github.midros.tmdb.data.model

import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
data class ObjectsCast(var cast: MutableList<ObjectsDetailsCast>) : Serializable

data class ObjectsDetailsCast(
        var character: String,
        var id: Int,
        var name: String,
        var profile_path: String
) : Serializable