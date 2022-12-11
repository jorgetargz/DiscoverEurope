package com.jorgetargz.europa.ui.list_paises

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ItemPaisBinding
import com.jorgetargz.europa.domain.modelo.Pais
import com.jorgetargz.europa.ui.utils.inflate
import com.jorgetargz.europa.ui.utils.loadUrl

class PaisesAdapter(val listPaisesActionsImpl: ListPaisesFragment.ListPaisesActionsImpl) : ListAdapter<Pais, PaisesAdapter.ItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            listPaisesActionsImpl,
            parent.inflate(R.layout.item_pais),
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    class ItemViewholder(
        val listPaisesActionsImpl: ListPaisesFragment.ListPaisesActionsImpl,
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemPaisBinding.bind(itemView)

        fun bind(item: Pais) = with(binding) {
            tvNombre.text = item.nombre
            tvCapital.text = item.capital
            tvNombreLocal.text = item.nombreLocal
            ivBandera.loadUrl(item.urlBandera)

            card.setOnClickListener {
                listPaisesActionsImpl.onPaisClicked(item.nombre)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Pais>() {
        override fun areItemsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return oldItem == newItem
        }
    }
}
