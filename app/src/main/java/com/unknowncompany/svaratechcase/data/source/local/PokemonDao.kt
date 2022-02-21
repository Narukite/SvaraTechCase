package com.unknowncompany.svaratechcase.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM caught_pokemon")
    fun getAllCaughtPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM caught_pokemon WHERE id = :id")
    fun getCaughtPokemonById(id: String): Flow<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(data: PokemonEntity)

    @Query("DELETE FROM caught_pokemon WHERE id = :id")
    suspend fun deletePokemonById(id: String)

}