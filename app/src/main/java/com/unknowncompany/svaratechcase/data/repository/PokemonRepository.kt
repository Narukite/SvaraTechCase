package com.unknowncompany.svaratechcase.data.repository

import com.unknowncompany.svaratechcase.data.source.PokemonMapper.toListOfPokemonItemModel
import com.unknowncompany.svaratechcase.data.source.PokemonMapper.toListPokemonItemAndNextModel
import com.unknowncompany.svaratechcase.data.source.PokemonMapper.toPokemonDetailsModel
import com.unknowncompany.svaratechcase.data.source.local.PokemonEntity
import com.unknowncompany.svaratechcase.data.source.local.PokemonLocalDataSource
import com.unknowncompany.svaratechcase.data.source.remote.PokemonDetailsResponse
import com.unknowncompany.svaratechcase.data.source.remote.PokemonRemoteDataSource
import com.unknowncompany.svaratechcase.data.source.remote.PokemonResponse
import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource,
) {

    suspend fun getPokemon(
        offset: Int,
        limit: Int,
    ): Flow<ResponseWrapper<ListPokemonItemAndNextModel>> {
        return object : FetchDataWrapper<PokemonResponse, ListPokemonItemAndNextModel>() {
            override suspend fun fetchData(): PokemonResponse =
                pokemonRemoteDataSource.getPokemon(offset, limit)

            override suspend fun mapData(response: PokemonResponse): ListPokemonItemAndNextModel =
                response.toListPokemonItemAndNextModel()

        }.getData().flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonDetails(id: String): Flow<ResponseWrapper<PokemonDetailsModel>> {
        return object : FetchDataWrapper<PokemonDetailsResponse, PokemonDetailsModel>() {
            override suspend fun fetchData(): PokemonDetailsResponse =
                pokemonRemoteDataSource.getPokemonDetails(id)

            override suspend fun mapData(response: PokemonDetailsResponse): PokemonDetailsModel =
                response.toPokemonDetailsModel()

        }.getData().flowOn(Dispatchers.IO)
    }

    fun getAllCaughtPokemon(): Flow<List<PokemonItemModel>> =
        pokemonLocalDataSource.getAllCaughtPokemon().map { it.toListOfPokemonItemModel() }

    fun getCaughtPokemonById(id: String): Flow<PokemonEntity> =
        pokemonLocalDataSource.getCaughtPokemonById(id)

    suspend fun insertPokemon(data: PokemonEntity) =
        pokemonLocalDataSource.insertPokemon(data)

    suspend fun deletePokemonById(id: String) =
        pokemonLocalDataSource.deletePokemonById(id)


}