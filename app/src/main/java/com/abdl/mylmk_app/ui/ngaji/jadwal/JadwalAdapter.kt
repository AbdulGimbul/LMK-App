package com.abdl.mylmk_app.ui.ngaji.jadwal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.databinding.ItemJadwalBinding
import java.text.SimpleDateFormat
import java.util.*

class JadwalAdapter : RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder>() {
    val jadwal = ArrayList<JadwalUserItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setJadwalList(jadwal: List<JadwalUserItem>?) {
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
        fun bind(jadwal: JadwalUserItem) {
            val sdf = SimpleDateFormat("HH.mm")
            val current = jadwal.jam
            val date = sdf.parse(current)
            val calendar = Calendar.getInstance()

            calendar.time = date
            calendar.add(Calendar.HOUR, 1)

            val tambahJam = sdf.format(calendar.time)

            with(binding) {
                tvMurid.text = jadwal.namaMurid
                tvHari.text = jadwal.hari
                tvPukul.text = "${jadwal.jam} - $tambahJam"
                tvGuru.text = jadwal.namaGuru
            }
        }
    }
}