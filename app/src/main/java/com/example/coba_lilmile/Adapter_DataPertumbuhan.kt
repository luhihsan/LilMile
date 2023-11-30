package com.example.coba_lilmile

import android.view.LayoutInflater
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView

class Adapter_DataPertumbuhan(
    private var list: List<DataUpdatePertumbuhan>,
    val itemClicked: OnItemClicked,
) : RecyclerView.Adapter<Adapter_DataPertumbuhan.AdapterPertumbuhanViewHolder>() {


    inner class AdapterPertumbuhanViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val tvTgl: TextView = view.findViewById(R.id.tvTgl_tumbuh)
        private val tvUsia: TextView = view.findViewById(R.id.tvUsia_tumbuh)
        private val tvBerat: TextView = view.findViewById(R.id.tvBerat)
        private val tvTinggi: TextView = view.findViewById(R.id.tvTinggi)
        private val bEdit: AppCompatImageButton = view.findViewById(R.id.bEdit)
        private val bDelete: AppCompatImageButton = view.findViewById(R.id.bDelete)

        fun bind(data: DataUpdatePertumbuhan) {
            tvTgl.text = data.tgl_tumbuh
            tvUsia.text = data.umur_tumbuh
            tvBerat.text = data.berat_tumbuh.toString()
            tvTinggi.text = data.tinggi_tumbuh.toString()

            bEdit.setOnClickListener {
                itemClicked.editClicked(data)
            }

            bDelete.setOnClickListener {
                itemClicked.deleteClicked(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_DataPertumbuhan.AdapterPertumbuhanViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pertumbuhan_anak, parent, false)
        return AdapterPertumbuhanViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: Adapter_DataPertumbuhan.AdapterPertumbuhanViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    // Tambahkan fungsi untuk mengupdate data di RecyclerView
    fun updateData(newList: List<DataUpdatePertumbuhan>) {
        list = newList
        notifyDataSetChanged()
    }
}

interface OnItemClicked {
    fun editClicked(data: DataUpdatePertumbuhan)
    fun deleteClicked(data: DataUpdatePertumbuhan)
}
