package com.abdl.mylmk_app.ui.home.guru

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.databinding.ItemGuruBinding
import com.abdl.mylmk_app.ui.home.detail.DetailGuruActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GuruAdapter : RecyclerView.Adapter<GuruAdapter.GuruViewHolder>() {
    var guru = ArrayList<GuruItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setGuruList(guru: List<GuruItem>?) {
        if (guru == null) return
        this.guru.clear()
        this.guru.addAll(guru)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuruViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemGuruBinding.inflate(inflater, parent, false)
        return GuruViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuruViewHolder, position: Int) {
        val guru = guru[position]
        if (guru != null) {
            holder.bind(guru)
        }

    }

    override fun getItemCount(): Int {
        return guru.size
    }

    class GuruViewHolder(val binding: ItemGuruBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guru: GuruItem) {
            with(binding) {
                tvItemName.text = guru.nama
                tvKompetensi.text = "Kompetensi : Guru Ngaji"
                tvUserDetail.text = guru.alamat

                Glide.with(itemView.context)
                    .load(guru.avatar)
                    .apply(RequestOptions().override(250, 250))
                    .into(imgItemPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailGuruActivity::class.java)
                    intent.putExtra(DetailGuruActivity.EXTRA_GURU, guru)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}