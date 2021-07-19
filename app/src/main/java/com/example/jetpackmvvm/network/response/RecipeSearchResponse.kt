package com.example.jetpackmvvm.network.response

import com.example.jetpackmvvm.network.model.RecipeNetworkEntity
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse(
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeNetworkEntity>
)