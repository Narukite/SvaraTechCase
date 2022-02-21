package com.unknowncompany.svaratechcase.ui.caughtpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.unknowncompany.svaratechcase.data.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class CaughtPokemonViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    val caughtPokemon = viewModelScope.async(Dispatchers.IO) {
        pokemonRepository.getAllCaughtPokemon().asLiveData()
    }

}