package com.unknowncompany.svaratechcase.ui.caughtpokemon

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.unknowncompany.svaratechcase.R
import com.unknowncompany.svaratechcase.databinding.ActivityCaughtPokemonBinding
import com.unknowncompany.svaratechcase.ui.pokemondetails.PokemonDetailsActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CaughtPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaughtPokemonBinding
    private val viewModel: CaughtPokemonViewModel by viewModel()

    private lateinit var pokemonAdapter: CaughtPokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaughtPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.caught_pokemon)
        initRecyclerView()

        lifecycleScope.launch {
            viewModel.caughtPokemon.await().observe(this@CaughtPokemonActivity, {
                binding.cpi.visibility = View.GONE
                pokemonAdapter.setData(it)
                if (it.isEmpty()) Toast.makeText(this@CaughtPokemonActivity,
                    getString(R.string.no_caught_pokemon),
                    Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initRecyclerView() {
        with(binding.rvPokemon) {
            val linearLayoutManager = LinearLayoutManager(this@CaughtPokemonActivity)
            layoutManager = linearLayoutManager
            val dividerItemDecoration =
                DividerItemDecoration(this@CaughtPokemonActivity, linearLayoutManager.orientation)
            addItemDecoration(dividerItemDecoration)

            setHasFixedSize(true)
            pokemonAdapter = CaughtPokemonAdapter {
                val intent = Intent(context, PokemonDetailsActivity::class.java)
                intent.putExtra(PokemonDetailsActivity.POKEMON_ID, it)
                startActivity(intent)
            }
            adapter = pokemonAdapter
        }
    }

}