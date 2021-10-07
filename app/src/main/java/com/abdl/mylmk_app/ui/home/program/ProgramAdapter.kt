package com.abdl.mylmk_app.ui.home.program

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdl.mylmk_app.data.source.local.entity.ProgramEntity
import com.abdl.mylmk_app.databinding.ItemProgramBinding

class ProgramAdapter : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {
    var programList = ArrayList<ProgramEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProgramList(program: List<ProgramEntity>) {
        if (program == null) return
        this.programList.clear()
        this.programList.addAll(program)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemProgramBinding.inflate(inflater, parent, false)
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programList[position]
        if (program != null) {
            holder.bind(program)
        }

        holder.itemView.setOnClickListener {
            program.expand = !program.expand
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = programList.size

    class ProgramViewHolder(val binding: ItemProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: ProgramEntity) {
            with(binding) {
                tvProgramTitle.text = program.judul
                tvProgramDescription.text = program.deskripsi

                expandedProgram.visibility = if (program.expand) View.VISIBLE else View.GONE
            }
        }
    }
}