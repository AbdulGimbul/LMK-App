package com.abdl.mylmk_app.ui.ngaji.guru

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.databinding.ItemGuruSayaBinding

class MyGuruAdapter : RecyclerView.Adapter<MyGuruAdapter.JadwalViewHolder>() {
    var jadwal = ArrayList<JadwalUserItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setJadwalList(jadwal: List<JadwalUserItem>?) {
        if (jadwal == null) return
        this.jadwal.clear()
        this.jadwal.addAll(jadwal)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemGuruSayaBinding.inflate(inflater, parent, false)
        return JadwalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JadwalViewHolder, position: Int) {
        val jadwal = jadwal[position]
        if (jadwal != null) {
            holder.bind(jadwal)
        }
    }

    override fun getItemCount(): Int {
        return jadwal.size
    }

    class JadwalViewHolder(val binding: ItemGuruSayaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jadwal: JadwalUserItem) {
            with(binding) {
                tvItemName.text = jadwal.namaGuru
                tvUserDetail.text = "Hari : ${jadwal.hari} | Pukul : ${jadwal.jam}"
            }
        }
    }
}