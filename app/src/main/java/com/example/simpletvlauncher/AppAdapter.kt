package com.example.simpletvlauncher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    private val items: List<AppItem>,
    private val onAppClick: (AppItem) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(items[position], onAppClick)
    }

    override fun getItemCount(): Int = items.size

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.imgIcon)
        private val nameView: TextView = itemView.findViewById(R.id.tvName)

        fun bind(item: AppItem, onAppClick: (AppItem) -> Unit) {
            iconView.setImageDrawable(item.icon)
            nameView.text = item.label

            itemView.setOnClickListener {
                onAppClick(item)
            }

            itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    itemView.scaleX = 1.05f
                    itemView.scaleY = 1.05f
                    itemView.alpha = 1.0f
                } else {
                    itemView.scaleX = 1f
                    itemView.scaleY = 1f
                    itemView.alpha = 0.95f
                }
            }
        }
    }
}
