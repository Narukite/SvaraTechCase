package com.unknowncompany.svaratechcase.data.source.local

import kotlinx.coroutines.flow.Flow

class PokemonLocalDataSource(private val pokemonDao: PokemonDao) {

    fun getAllCaughtPokemon(): Flow<List<PokemonEntity>> =
        pokemonDao.getAllCaughtPokemon()

    fun getCaughtPokemonById(id: String): Flow<PokemonEntity> =
        pokemonDao.getCaughtPokemonById(id)

    suspend fun insertPokemon(data: PokemonEntity) =
        pokemonDao.insertPokemon(data)

    suspend fun deletePokemonById(id: String) =
        pokemonDao.deletePokemonById(id)

}