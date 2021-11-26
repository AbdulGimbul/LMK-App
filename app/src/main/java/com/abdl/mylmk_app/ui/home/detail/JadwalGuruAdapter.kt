package com.abdl.mylmk_app.ui.home.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.JadwalGuruItem
import com.abdl.mylmk_app.databinding.ItemJadwalGuruBinding

class JadwalGuruAdapter : RecyclerView.Adapter<JadwalGuruAdapter.JadwalGuruViewHolder>() {
    val jadwal = ArrayList<JadwalGuruItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setJadwalList(jadwal: List<JadwalGuruItem>?) {
        if (jadwal == null) return
        this.jadwal.clear()
        this.jadwal.addAll(jadwal)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JadwalGuruViewHolder {
        val binding =
            ItemJadwalGuruBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JadwalGuruViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JadwalGuruViewHolder, position: Int) {
        val jadwal = jadwal[position]
        holder.bind(jadwal)
    }

    override fun getItemCount(): Int {
        return jadwal.size
    }

    inner class JadwalGuruViewHolder(val binding: ItemJadwalGuruBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jadwal: JadwalGuruItem) {
            with(binding) {
                tvHari.text = jadwal.hari
                tvPukul.text = jadwal.jam
                tvMurid.text = jadwal.namaMurid
            }
        }
    }
}