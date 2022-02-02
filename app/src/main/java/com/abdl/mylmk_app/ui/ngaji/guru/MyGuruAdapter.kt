package com.abdl.mylmk_app.ui.ngaji.guru

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.databinding.ItemGuruSayaBinding
import com.abdl.mylmk_app.ui.home.detail.DetailGuruActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class MyGuruAdapter : RecyclerView.Adapter<MyGuruAdapter.JadwalViewHolder>() {
    var jadwal = ArrayList<JadwalUserItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setJadwalList(jadwal: List<JadwalUserItem>?) {
        if (jadwal == null) return
        this.jadwal.clear()
        this.jadwal.addAll(jadwal.distinctBy { it.namaGuru })
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
            val sdf = SimpleDateFormat("HH.mm")
            val current = jadwal.jam
            val date = sdf.parse(current)
            val calendar = Calendar.getInstance()

            calendar.time = date
            calendar.add(Calendar.HOUR, 1)

            val tambahJam = sdf.format(calendar.time)

            with(binding) {
                tvMurid.text = "Guru ngaji ${jadwal.namaMurid}"
                tvItemName.text = jadwal.namaGuru
                tvUserDetail.text = "Alamat : ${jadwal.alamatGuru}"
                Glide.with(itemView.context)
                    .load(jadwal.avatarGuru)
                    .apply(RequestOptions().override(150, 150))
                    .into(imgItemPhoto)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailGuruActivity::class.java)
                    intent.putExtra(DetailGuruActivity.EXTRA_MY_GURU, jadwal)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}