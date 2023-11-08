package com.example.coba_lilmile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class Tambah_DataPertumbuhan : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data_pertumbuhan)
    }

    private var etTgl_tumbuh: EditText? = null
    fun showDatePicker(view: View?) {
        val year: Int
        val month: Int
        val day: Int
        // Tentukan tanggal awal yang ingin ditampilkan dalam DatePickerDialog
        // Misalnya, di sini kita menampilkan tanggal hari ini.
        val calendar: Calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)

        // Buat DatePickerDialog
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, day -> // Handle tanggal yang dipilih oleh pengguna
                val selectedDate = year.toString() + "-" + (month + 1) + "-" + day
                etTgl_tumbuh?.setText(selectedDate)
            }, year, month, day
        )

        // Tampilkan DatePickerDialog
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


            // Selanjutnya, Anda dapat menyimpan data ini ke penyimpanan atau melakukan operasi lain yang diperlukan.
            // Contohnya: Simpan ke database atau penyimpanan lokal.
        }
    }




}