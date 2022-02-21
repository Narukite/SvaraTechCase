package com.unknowncompany.svaratechcase.di

import com.unknowncompany.svaratechcase.ui.caughtpokemon.CaughtPokemonViewModel
import com.unknowncompany.svaratechcase.ui.mainactivity.MainActivityViewModel
import com.unknowncompany.svaratechcase.ui.pokemondetails.PokemonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { PokemonDetailsViewModel(get()) }
    viewModel { CaughtPokemonViewModel(get()) }
}