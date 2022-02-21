package com.unknowncompany.svaratechcase.ui.pokemondetails

import androidx.lifecycle.*
import com.unknowncompany.svaratechcase.data.repository.PokemonDetailsModel
import com.unknowncompany.svaratechcase.data.repository.PokemonRepository
import com.unknowncompany.svaratechcase.data.source.local.PokemonEntity
import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import com.unknowncompany.svaratechcase.data.source.remote.runFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val pokemonId = MutableLiveData<String>()
    fun fetchCaughtPokemon(pokemonId: String) {
        this.pokemonId.value = pokemonId
    }

    private val _pokemonDetails = MutableLiveData<PokemonDetailsModel>()
    val pokemonDetails: LiveData<PokemonDetailsModel>
        get() = _pokemonDetails

    fun fetchPokemonDetails(
        onLoading: (() -> Unit)? = null,
        onFail: ((ResponseWrapper<*>) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            pokemonId.value?.let {
                pokemonRepository.getPokemonDetails(it)
                    .runFlow(::fetchPokemonDetailsOnSuccess, onLoading, onFail)
            }
        }
    }

    private fun fetchPokemonDetailsOnSuccess(data: PokemonDetailsModel) {
        _pokemonDetails.value = data
    }

    val caughtPokemon = pokemonId.switchMap {
        pokemonRepository.getCaughtPokemonById(it).flowOn(Dispatchers.IO).asLiveData()
    }

    fun insertPokemon(name: String) = viewModelScope.launch(Dispatchers.IO) {
        pokemonDetails.value?.let { PokemonEntity(it.id, name) }?.let {
            pokemonRepository.insertPokemon(it)
        }
    }

    fun deletePokemon() = viewModelScope.launch(Dispatchers.IO) {
        pokemonDetails.value?.let { pokemonRepository.deletePokemonById(it.id) }
    }

}