package com.unknowncompany.svaratechcase.data.source.remote

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null,
)

data class ResultsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
)
