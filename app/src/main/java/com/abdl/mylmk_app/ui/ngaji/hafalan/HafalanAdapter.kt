package com.abdl.mylmk_app.ui.ngaji.hafalan


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.local.entity.HafalanEntity
import com.abdl.mylmk_app.databinding.ItemHafalanBinding

class HafalanAdapter(private val listener: OnItemClickListener) :
    ListAdapter<HafalanEntity, HafalanAdapter.HafalanViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HafalanViewHolder {
        val binding = ItemHafalanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HafalanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HafalanViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class HafalanViewHolder(private val binding: ItemHafalanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val hafalan = getItem(position)
                        listener.onItemClick(hafalan)
                    }
                }
                checkboxCompleted.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val hafalan = getItem(position)
                        listener.onCheckBoxClick(hafalan, checkboxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(hafalan: HafalanEntity) {
            binding.apply {
                checkboxCompleted.isChecked = hafalan.completed
                tvHafalan.text = hafalan.name
                tvHafalan.paint.isStrikeThruText = hafalan.completed
                labelPriority.isVisible = hafalan.important
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(hafalan: HafalanEntity)
        fun onCheckBoxClick(hafalan: HafalanEntity, isChecked: Boolean)
    }

    class DiffCallback : DiffUtil.ItemCallback<HafalanEntity>() {
        override fun areItemsTheSame(oldItem: HafalanEntity, newItem: HafalanEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: HafalanEntity, newItem: HafalanEntity) =
            oldItem == newItem
    }
}