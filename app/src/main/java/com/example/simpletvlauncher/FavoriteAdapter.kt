package com.example.simpletvlauncher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(
    private val items: List<AppItem>,
    private val onAppClick: (AppItem) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position], onAppClick)
    }

    override fun getItemCount(): Int = items.size

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.imgFavoriteIcon)
        private val nameView: TextView = itemView.findViewById(R.id.tvFavoriteName)

        fun bind(item: AppItem, onAppClick: (AppItem) -> Unit) {
            iconView.setImageDrawable(item.icon)
            nameView.text = item.label

            itemView.setOnClickListener {
                onAppClick(item)
            }

            itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    itemView.scaleX = 1.08f
                    itemView.scaleY = 1.08f
                } else {
                    itemView.scaleX = 1f
                    itemView.scaleY = 1f
                }
            }
        }
    }
}
