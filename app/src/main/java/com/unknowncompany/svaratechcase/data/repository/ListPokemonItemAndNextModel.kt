package com.unknowncompany.svaratechcase.data.repository

data class ListPokemonItemAndNextModel(
    val listPokemon: List<PokemonItemModel>,
    val next: Boolean,
)
