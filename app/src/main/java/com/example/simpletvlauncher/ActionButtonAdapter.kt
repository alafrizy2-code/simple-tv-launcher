package com.example.simpletvlauncher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActionButtonAdapter(
    private val items: List<ActionButtonItem>,
    private val onActionClick: (ActionButtonItem) -> Unit
) : RecyclerView.Adapter<ActionButtonAdapter.ActionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.widget_action_button, parent, false)
        return ActionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(items[position], onActionClick)
    }

    override fun getItemCount(): Int = items.size

    class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labelView: TextView = itemView.findViewById(R.id.actionLabel)

        fun bind(item: ActionButtonItem, onActionClick: (ActionButtonItem) -> Unit) {
            labelView.text = item.label

            itemView.setOnClickListener {
                onActionClick(item)
            }

            itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    itemView.scaleX = 1.05f
                    itemView.scaleY = 1.05f
                } else {
                    itemView.scaleX = 1f
                    itemView.scaleY = 1f
                }
            }
        }
    }
}
