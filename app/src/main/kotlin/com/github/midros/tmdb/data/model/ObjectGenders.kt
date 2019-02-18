package com.github.midros.tmdb.data.model

import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
data class ObjectGenders(val genres: MutableList<ObjectListGenders>) : Serializable

data class ObjectListGenders(val id: Int, val name: String) : Serializable