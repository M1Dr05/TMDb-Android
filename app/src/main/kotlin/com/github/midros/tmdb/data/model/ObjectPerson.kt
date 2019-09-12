package com.github.midros.tmdb.data.model

import java.io.Serializable


/**
 * Created by luis rafael on 16/02/19.
 */
data class PojoDetailsPerson(
        var biography: String,
        var birthday: String,
        var deathday:String,
        var id:Int,
        var popularity:Float,
        var homepage: String,
        var name: String,
        var place_of_birth: String,
        var profile_path: String
) : Serializable

data class ObjectPerson(
        var page: Int,
        var total_pages: Int,
        var results: MutableList<PojoResultsPerson>
):Serializable

data class PojoResultsPerson(
        var popularity:Float,
        var id:Int,
        var profile_path:String,
        var name:String
):Serializable