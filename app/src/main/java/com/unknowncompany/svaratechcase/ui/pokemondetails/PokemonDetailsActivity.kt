package com.unknowncompany.svaratechcase.ui.pokemondetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.unknowncompany.svaratechcase.BuildConfig
import com.unknowncompany.svaratechcase.R
import com.unknowncompany.svaratechcase.data.source.remote.ResponseWrapper
import com.unknowncompany.svaratechcase.databinding.ActivityPokemonDetailsBinding
import com.unknowncompany.svaratechcase.ui.caughtpokemon.NameDialog
import com.unknowncompany.svaratechcase.utils.Const
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random.Default.nextBoolean

class PokemonDetailsActivity : AppCompatActivity() {

    companion object {
        const val POKEMON_ID = "pokemonId"
    }

    private lateinit var binding: ActivityPokemonDetailsBinding
    private val detailsViewModel: PokemonDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = ""

        val pokemonId = intent?.getStringExtra(POKEMON_ID) ?: ""
        if (pokemonId.isBlank()) finish()

        detailsViewModel.pokemonDetails.observe(this, { pokemonDetails ->
            with(binding) {
                val iconUrl =
                    "${BuildConfig.POKEMON_OFFICIAL_ARTWORK_API}${pokemonDetails.id}${Const.ICON_EXT}"
                Glide.with(this@PokemonDetailsActivity).load(iconUrl).into(ivIcon)
                tvName.text = pokemonDetails.name
                title = pokemonDetails.name
                tvHeight.text =
                    getString(R.string.height_template, pokemonDetails.height.toString())
                tvWeight.text =
                    getString(R.string.weight_template, pokemonDetails.weight.toString())
                tvBaseExperience.text =
                    getString(R.string.base_experience_template,
                        pokemonDetails.baseExperience.toString())

                detailsViewModel.caughtPokemon.observe(this@PokemonDetailsActivity,
                    { caughtPokemon ->
                        val isCaught = caughtPokemon != null
                        setFabIcon(isCaught)
                        fabCatch.setOnClickListener {
                            if (isCaught) {
                                detailsViewModel.deletePokemon()
                                Toast.makeText(this@PokemonDetailsActivity,
                                    getString(R.string.you_released_name, pokemonDetails.name),
                                    LENGTH_SHORT).show()
                            } else {
                                val lucky = nextBoolean()
                                if (lucky) NameDialog { name ->
                                    detailsViewModel.insertPokemon(name)
                                    Toast.makeText(this@PokemonDetailsActivity,
                                        getString(R.string.you_caught_name, name),
                                        LENGTH_SHORT).show()
                                }.show(supportFragmentManager, NameDialog::class.simpleName)
                                else Toast.makeText(this@PokemonDetailsActivity,
                                    getString(R.string.you_failed_to_caught_name,
                                        pokemonDetails.name),
                                    LENGTH_SHORT).show()
                            }
                        }
                        cpi.visibility = GONE
                    })
            }
        })
        detailsViewModel.fetchCaughtPokemon(pokemonId)
        detailsViewModel.fetchPokemonDetails(::basicOnLoadingWhenFetching,
            ::basicOnFailWhenFetching)
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
        Log.d(PokemonDetailsActivity::class.simpleName, "basicOnFailWhenFetching: $message")
    }

    private fun setFabIcon(isCaught: Boolean) {
        val fabDrawableId =
            if (isCaught) R.drawable.ic_white_check_24 else R.drawable.ic_white_catching_pokemon_24
        binding.fabCatch.setImageDrawable(ContextCompat.getDrawable(this, fabDrawableId))
    }

}