package com.jorgetargz.europa.ui.list_ciudades

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ItemPaisBinding
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.ui.utils.inflate

class CiudadesAdapter(val listPaisesActionsImpl: ListCiudadesActions) :
    ListAdapter<Ciudad, CiudadesAdapter.ItemViewholder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            listPaisesActionsImpl,
            parent.inflate(R.layout.item_ciudad),
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    class ItemViewholder(
        val listPaisesActionsImpl: ListCiudadesActions,
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPaisBinding.bind(itemView)

        fun bind(item: Ciudad) = with(binding) {
            tvNombre.text = item.nombre

            card.setOnClickListener {
                listPaisesActionsImpl.onPaisClicked(item.nombre)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ciudad>() {
        override fun areItemsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem == newItem
        }
    }
}
