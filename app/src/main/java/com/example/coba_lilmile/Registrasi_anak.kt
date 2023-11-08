package com.example.coba_lilmile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class Registrasi_anak : AppCompatActivity() {

    private var etDateOfBirth: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi_anak)

        etDateOfBirth = findViewById(R.id.etDateOfBirth)
    }

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
                etDateOfBirth?.setText(selectedDate)
            }, year, month, day
        )

        // Tampilkan DatePickerDialog
        datePickerDialog.show()
    }

    fun simpanDataAnak(view: View) {
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etDateOfBirth = findViewById<EditText>(R.id.etDateOfBirth)
        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etHeadCircumference = findViewById<EditText>(R.id.etHeadCircumference)
        val spAdditionalProfile = findViewById<Spinner>(R.id.spAdditionalProfile)

        // Ambil nilai dari kolom-kolom

        val fullName = etFullName.text.toString().trim()
        val dateOfBirth = etDateOfBirth.text.toString().trim()
        val selectedGender = when (rgGender.checkedRadioButtonId) {
            R.id.rbMale -> "Laki-laki"
            R.id.rbFemale -> "Perempuan"
            else -> ""
        }
        val height = etHeight.text.toString().trim()
        val weight = etWeight.text.toString().trim()
        val headCircumference = etHeadCircumference.text.toString().trim()
        val selectedBloodType = spAdditionalProfile.selectedItem.toString()
        if (fullName.isEmpty() || dateOfBirth.isEmpty() || selectedGender.isEmpty() || height.isEmpty() || weight.isEmpty() || headCircumference.isEmpty() || selectedBloodType == "Pilih Golongan Darah" ) {
            // Tambahkan validasi untuk kolom-kolom lainnya jika diperlukan
            Toast.makeText(this, "Harap isi semua kolom.", Toast.LENGTH_SHORT).show()

            // Jika kolom "Nama Lengkap" kosong, arahkan fokus ke kolom tersebut
            if (fullName.isEmpty()) {
                etFullName.requestFocus()
            } else if (dateOfBirth.isEmpty()) {
                etDateOfBirth.requestFocus()
            } else if (selectedGender.isEmpty()) {
                // Tidak perlu arahkan fokus pada RadioGroup, karena pengguna hanya bisa memilih dari RadioButton
                // Yang dipilih adalah RadioButton di dalam RadioGroup, bukan RadioGroup itu sendiri.
            } else if (height.isEmpty()) {
                etHeight.requestFocus()
            } else if (weight.isEmpty()) {
                etWeight.requestFocus()
            } else if (headCircumference.isEmpty()) {
                etHeadCircumference.requestFocus()
            } else if (selectedBloodType == "Pilih Golongan Darah") {
                spAdditionalProfile.requestFocus()
            }
            // Tambahkan blok serupa untuk kolom-kolom lain yang wajib
        } else {
            // Semua kolom yang wajib diisi telah terisi, Anda dapat melanjutkan menyimpan data.
            // Lakukan operasi penyimpanan di sini.
        }
    }


}
