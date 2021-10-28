package com.abdl.mylmk_app.ui.ngaji.jadwal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.local.entity.JadwalEntity
import com.abdl.mylmk_app.databinding.ItemJadwalBinding

class JadwalAdapter : RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder>() {
    val jadwal = ArrayList<JadwalEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setJadwalList(jadwal: List<JadwalEntity>) {
        if (jadwal == null) return
        this.jadwal.clear()
        this.jadwal.addAll(jadwal)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalViewHolder {
        val binding = ItemJadwalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JadwalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JadwalViewHolder, position: Int) {
        val jadwal = jadwal[position]
        holder.bind(jadwal)
    }

    override fun getItemCount(): Int {
        return jadwal.size
    }

    inner class JadwalViewHolder(val binding: ItemJadwalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jadwal: JadwalEntity) {
            with(binding) {
                tvHari.text = jadwal.hari
                tvPukul.text = jadwal.jam
                tvGuru.text = jadwal.namaGuru
            }
        }
    }
}