package com.isel.dam.a37726.pokedex.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isel.dam.a37726.pokedex.R
import com.isel.dam.a37726.pokedex.databinding.ItemTypeBinding
import com.isel.dam.a37726.pokedex.model.data.PokemonType

class TypeAdapter(
    private val list: List<PokemonType>,
    private val context: Context
) : RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemTypeBinding.bind(itemView)
        fun bindView(item: PokemonType) {
            viewBinding.type = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}