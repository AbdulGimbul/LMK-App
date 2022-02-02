package com.abdl.mylmk_app.ui.home.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.JadwalGuruItem
import com.abdl.mylmk_app.databinding.ItemJadwalGuruBinding
import java.text.SimpleDateFormat
import java.util.*

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
            val sdf = SimpleDateFormat("HH.mm")
            val current = jadwal.jam
            val date = sdf.parse(current)
            val calendar = Calendar.getInstance()

            calendar.time = date
            calendar.add(Calendar.HOUR, 1)

            val tambahJam = sdf.format(calendar.time)

            with(binding) {
                tvHari.text = jadwal.hari
                tvPukul.text = "${jadwal.jam} - $tambahJam"
                tvMurid.text = jadwal.namaMurid
            }
        }
    }
}