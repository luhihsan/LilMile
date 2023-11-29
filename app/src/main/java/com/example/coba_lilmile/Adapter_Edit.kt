package com.example.coba_lilmile

import android.app.Dialog
import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

fun showEditDialog(context: Context, dataPertumbuhan: DataUpdatePertumbuhan, db: FirebaseDatabase) {
    val dialog = Dialog(context)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.edit_pertumbuhan_anak)
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    val etBerat = dialog.findViewById(R.id.etEditBerat) as EditText
    val etTinggi = dialog.findViewById(R.id.etEditTinggi) as EditText

    // Convert Double values to String before setting them to EditText
    etBerat.setText(dataPertumbuhan.berat_tumbuh?.toString())
    etTinggi.setText(dataPertumbuhan.tinggi_tumbuh?.toString())

    val bEditOk = dialog.findViewById(R.id.bEditOk) as Button
    val bEditCancel = dialog.findViewById(R.id.bEditCancel) as Button

    bEditOk.setOnClickListener {
        val berat = etBerat.text.toString().trim()
        val tinggi = etTinggi.text.toString().trim()

        if (berat.isNotEmpty() && tinggi.isNotEmpty()) {
            val updatedData = mapOf<String, Any>(
                "berat_tumbuh" to berat.toDouble(),
                "tinggi_tumbuh" to tinggi.toDouble()
            )

            // Update Data
            val key = dataPertumbuhan.id ?: ""
            try {
                db.getReference("pertumbuhan_anak").child(key)
                    .updateChildren(updatedData)
                    .addOnSuccessListener {
                        dialog.dismiss()
                        Toast.makeText(context, "Data berhasil terupdate", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Gagal terupdate: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    bEditCancel.setOnClickListener { dialog.dismiss() }
    dialog.show()
}
