package com.unknowncompany.svaratechcase.ui.caughtpokemon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unknowncompany.svaratechcase.BuildConfig
import com.unknowncompany.svaratechcase.R
import com.unknowncompany.svaratechcase.data.repository.PokemonItemModel
import com.unknowncompany.svaratechcase.databinding.ItemPokemonBinding
import com.unknowncompany.svaratechcase.utils.Const

class CaughtPokemonAdapter(
    private val onClickCallback: (pokemonId: String) -> Unit,
) :
    RecyclerView.Adapter<CaughtPokemonAdapter.ViewHolder>() {

    private var listPokemon: ArrayList<PokemonItemModel> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listPokemon: List<PokemonItemModel>) {
        this.listPokemon.clear()
        this.listPokemon.addAll(listPokemon)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(position: Int) {
            val item = listPokemon[position]
            val iconUrl = "${BuildConfig.POKEMON_OFFICIAL_ARTWORK_API}${item.id}${Const.ICON_EXT}"
            Glide.with(itemView.context).load(iconUrl).into(binding.ivIcon)
            binding.tvName.text = item.name

            binding.cl.setOnClickListener { onClickCallback.invoke(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = listPokemon.size
}