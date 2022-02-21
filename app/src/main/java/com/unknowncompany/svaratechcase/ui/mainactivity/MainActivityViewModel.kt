package com.unknowncompany.svaratechcase.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unknowncompany.svaratechcase.data.repository.ListPokemonItemAndNextModel
import com.unknowncompany.svaratechcase.data.repository.PokemonItemModel
import com.unknowncompany.svaratechcase.data.repository.PokemonRepository
import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import com.unknowncompany.svaratechcase.data.source.remote.runFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private var offset = 0
    private val limit = 20
    private val _listPokemon = MutableLiveData<List<PokemonItemModel>>()
    val listPokemon: LiveData<List<PokemonItemModel>>
        get() = _listPokemon

    private var endOfList = false
    private val _noMoreList = MutableLiveData<Boolean>()
    val noMoreList: LiveData<Boolean>
        get() = _noMoreList

    fun fetchListPokemon(
        onLoading: (() -> Unit)? = null,
        onFail: ((ResponseWrapper<*>) -> Unit)? = null,
    ) {
        if (endOfList) _noMoreList.value = endOfList
        else viewModelScope.launch {
            pokemonRepository.getPokemon(offset, limit)
                .runFlow(::fetchListPokemonOnSuccess, onLoading, onFail)
        }
    }

    private fun fetchListPokemonOnSuccess(data: ListPokemonItemAndNextModel) {
        this.offset += data.listPokemon.size
        this.endOfList = data.next
        _listPokemon.value = data.listPokemon
    }

}