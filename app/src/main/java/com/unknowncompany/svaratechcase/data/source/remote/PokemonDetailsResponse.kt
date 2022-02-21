package com.unknowncompany.svaratechcase.data.source.remote

import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(

    @field:SerializedName("base_experience")
    val baseExperience: Int? = null,

    @field:SerializedName("weight")
    val weight: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("height")
    val height: Int? = null,
)
