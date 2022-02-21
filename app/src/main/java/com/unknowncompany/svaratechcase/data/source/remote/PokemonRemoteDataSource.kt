package com.unknowncompany.svaratechcase.data.source.remote

class PokemonRemoteDataSource(private val pokemonService: PokemonService) {

    suspend fun getPokemon(offset: Int, limit: Int): PokemonResponse =
        pokemonService.getPokemon(offset, limit)

    suspend fun getPokemonDetails(id: String): PokemonDetailsResponse =
        pokemonService.getPokemonDetails(id)

}