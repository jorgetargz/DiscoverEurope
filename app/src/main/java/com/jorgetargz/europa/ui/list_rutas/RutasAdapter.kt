package com.jorgetargz.europa.ui.list_rutas

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ItemRutaBinding
import com.jorgetargz.europa.domain.modelo.Ruta
import com.jorgetargz.europa.ui.utils.inflate

class RutasAdapter(val listRutasActions: ListRutasActions) :
    ListAdapter<Ruta, RutasAdapter.ItemViewholder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            listRutasActions,
            parent.inflate(R.layout.item_ruta),
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    class ItemViewholder(
        val listRutasActions: ListRutasActions,
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemRutaBinding.bind(itemView)

        fun bind(item: Ruta) = with(binding) {
            tvCiudadInicio.text = item.ciudadInicio.nombre
            tvCiudadFin.text = item.ciudadFin.nombre

            card.setOnClickListener {
                listRutasActions.onRutaClicked(item)
            }

        }
    }

    val touchHelper = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                val position = viewHolder.bindingAdapterPosition
                val ruta = getItem(position)
                listRutasActions.onRutaSwipedLeft(ruta)
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_paper_bin)!!
            val itemView = viewHolder.itemView
            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            if (dX < 0) {
                icon.setBounds(
                    itemView.right - iconMargin - icon.intrinsicWidth,
                    itemView.top + iconMargin,
                    itemView.right - iconMargin,
                    itemView.bottom - iconMargin
                )
                icon.draw(c)
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Ruta>() {
        override fun areItemsTheSame(oldItem: Ruta, newItem: Ruta): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ruta, newItem: Ruta): Boolean {
            return oldItem == newItem
        }
    }
}