package com.unknowncompany.svaratechcase.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.unknowncompany.svaratechcase.R
import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import com.unknowncompany.svaratechcase.databinding.ActivityMainBinding
import com.unknowncompany.svaratechcase.ui.caughtpokemon.CaughtPokemonActivity
import com.unknowncompany.svaratechcase.ui.pokemondetails.PokemonDetailsActivity
import com.unknowncompany.svaratechcase.ui.pokemondetails.PokemonDetailsActivity.Companion.POKEMON_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModel()

    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.look_at_all_these_pokemons)
        initRecyclerView()

        viewModel.listPokemon.observe(this, {
            binding.cpi.visibility = View.GONE
            pokemonAdapter.setData(it)
        })
        viewModel.noMoreList.observe(this, {
            Toast.makeText(this, getString(R.string.there_are_no_more_pokemons), Toast.LENGTH_SHORT)
                .show()
        })
        viewModel.fetchListPokemon(::basicOnLoadingWhenFetching, ::basicOnFailWhenFetching)
    }

    private fun initRecyclerView() {
        with(binding.rvPokemon) {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager
            val dividerItemDecoration =
                DividerItemDecoration(this@MainActivity, linearLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            setHasFixedSize(true)
            pokemonAdapter = PokemonAdapter({
                viewModel.fetchListPokemon(
                    ::basicOnLoadingWhenFetching,
                    ::basicOnFailWhenFetching)
            }, {
                val intent = Intent(context, PokemonDetailsActivity::class.java)
                intent.putExtra(POKEMON_ID, it)
                startActivity(intent)
            })
            adapter = pokemonAdapter
        }
    }

    private fun basicOnLoadingWhenFetching() {
        binding.cpi.visibility = View.VISIBLE
    }

    private fun basicOnFailWhenFetching(responseWrapper: ResponseWrapper<*>) {
        val message = when (responseWrapper) {
            is ResponseWrapper.Error -> responseWrapper.message
            is ResponseWrapper.HttpError -> responseWrapper.message
            is ResponseWrapper.NetworkError -> responseWrapper.message
            else -> "Unidentified"
        }
        Log.d(MainActivity::class.simpleName, "basicOnFailWhenFetching: $message")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_caught_pokemon -> {
                startActivity(Intent(this, CaughtPokemonActivity::class.java))
                return true
            }
        }
        return true
    }

}