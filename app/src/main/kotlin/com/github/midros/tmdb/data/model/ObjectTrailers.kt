package com.github.midros.tmdb.data.model

import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
class ObjectTrailers(
        var id: Int,
        var results: MutableList<ObjectDetailsTrailers>
) : Serializable

class ObjectDetailsTrailers(
        var key: String,
        var name: String,
        var size: String
) : Serializable
