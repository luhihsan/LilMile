package com.example.coba_lilmile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar
import java.util.UUID

class Tambah_DataPertumbuhan : AppCompatActivity() {

    private lateinit var etTgl_tumbuh: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var preference: PreferenceHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data_pertumbuhan)

        preference = PreferenceHelper(this)
        etTgl_tumbuh = findViewById(R.id.etTgl_tumbuh)
        etTgl_tumbuh.setOnClickListener {
            showDatePicker()
        }



        databaseReference = FirebaseDatabase.getInstance().reference
    }

    private fun showDatePicker() {
        val year: Int
        val month: Int
        val day: Int
        val calendar: Calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = "$year-${month + 1}-$day"
                etTgl_tumbuh.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    fun simpanDataPertumbuhan(view: View) {
        val etTanggal = findViewById<EditText>(R.id.etTgl_tumbuh)
        val etUmur = findViewById<EditText>(R.id.etUmur_tumbuh)
        val etBerat = findViewById<EditText>(R.id.etBerat_tumbuh)
        val etTinggi = findViewById<EditText>(R.id.etTinggi_tumbuh)

        val tanggal = etTanggal.text.toString().trim()
        val umur = etUmur.text.toString().toIntOrNull() ?: 0
        val berat = etBerat.text.toString().toDoubleOrNull() ?: 0.0
        val tinggi = etTinggi.text.toString().toDoubleOrNull() ?: 0.0

        val id_akun = preference.getValues("id")

        if (tanggal.isEmpty() || umur == 0 || berat == 0.0 || tinggi == 0.0) {
            Toast.makeText(this, "Harap isi semua kolom yang wajib diisi.", Toast.LENGTH_SHORT).show()

            if (tanggal.isEmpty()) {
                etTanggal.requestFocus()
            } else if (umur == 0) {
                etUmur.requestFocus()
            } else if (berat == 0.0) {
                etBerat.requestFocus()
            } else if (tinggi == 0.0) {
                etTinggi.requestFocus()
            }
        } else {
            // Create a unique ID for each data entry
            val uid = UUID.randomUUID().toString()

            // Create DataUpdatePertumbuhan object
            val dataPertumbuhan = DataUpdatePertumbuhan(
                id = uid,
                idAkun = id_akun,
                tgl_tumbuh = tanggal,
                umur_tumbuh = umur.toString(),
                tinggi_tumbuh = tinggi,
                berat_tumbuh = berat
            )

            // Save the data to the database
            val key = databaseReference.child("pertumbuhan_anak").push().key
            if (key != null) {
                databaseReference.child("pertumbuhan_anak").child(uid).setValue(dataPertumbuhan)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data pertumbuhan disimpan.", Toast.LENGTH_SHORT).show()
                        // Continue with other operations after successful save
                        
                        val intent = Intent(this@Tambah_DataPertumbuhan, ViewChart::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan data pertumbuhan.", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Panggil intent untuk memulai ulang aktivitas sebelumnya
        val intent = Intent(this, ViewChart::class.java)
        startActivity(intent)

        // Tutup aktivitas saat ini
        finish()
    }

}
