package com.jorgetargz.europa.ui.list_ciudades

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jorgetargz.europa.R
import com.jorgetargz.europa.databinding.ItemCiudadBinding
import com.jorgetargz.europa.domain.modelo.Ciudad
import com.jorgetargz.europa.ui.utils.inflate

class CiudadesAdapter(val listCiudadesActions: ListCiudadesActions) :
    ListAdapter<Ciudad, CiudadesAdapter.ItemViewholder>(
        DiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            parent.inflate(R.layout.item_ciudad),
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }

    class ItemViewholder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemCiudadBinding.bind(itemView)

        fun bind(item: Ciudad) = with(binding) {
            tvCiudad.text = item.nombre
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
                val ciudad = getItem(position)
                listCiudadesActions.onCitySwipedLeft(ciudad)
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

    class DiffCallback : DiffUtil.ItemCallback<Ciudad>() {
        override fun areItemsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem.nombre == newItem.nombre
        }

        override fun areContentsTheSame(oldItem: Ciudad, newItem: Ciudad): Boolean {
            return oldItem == newItem
        }
    }
}
