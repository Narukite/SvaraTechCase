package com.unknowncompany.svaratechcase.data.source

import com.unknowncompany.svaratechcase.data.repository.ListPokemonItemAndNextModel
import com.unknowncompany.svaratechcase.data.repository.PokemonDetailsModel
import com.unknowncompany.svaratechcase.data.repository.PokemonItemModel
import com.unknowncompany.svaratechcase.data.source.local.PokemonEntity
import com.unknowncompany.svaratechcase.data.source.remote.PokemonDetailsResponse
import com.unknowncompany.svaratechcase.data.source.remote.PokemonResponse

object PokemonMapper {

    fun PokemonResponse.toListPokemonItemAndNextModel(): ListPokemonItemAndNextModel {
        val listPokemon = arrayListOf<PokemonItemModel>()
        val next = this.next.isNullOrBlank()

        if (results != null) for (item in results) {
            val url = item?.url?.split("/")
            val id = url?.get(6) ?: ""
            val name = item?.name ?: ""
            listPokemon.add(PokemonItemModel(id, name))
        }

        return ListPokemonItemAndNextModel(listPokemon, next)
    }

    fun PokemonDetailsResponse.toPokemonDetailsModel(): PokemonDetailsModel {
        val id = id?.toString() ?: ""
        val name = name ?: ""
        val height = height ?: -1
        val weight = weight ?: -1
        val baseExperience = baseExperience ?: -1
        return PokemonDetailsModel(id, name, height, weight, baseExperience)
    }

    fun List<PokemonEntity>.toListOfPokemonItemModel(): List<PokemonItemModel> {
        val newList = arrayListOf<PokemonItemModel>()
        for (item in this) newList.add(PokemonItemModel(item.id, item.name))
        return newList
    }

}