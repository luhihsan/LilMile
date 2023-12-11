package com.example.coba_lilmile

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.TextView
import java.util.Calendar

class ReservasiActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{
    var tanggal = 0
    var bulan = 0
    var tahun = 0
    lateinit var teksTanggal: TextView
    private lateinit var rg_kelamin: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasi_dokter)
    }

    fun getTanggal(){
        val kal: Calendar = Calendar.getInstance()
        tanggal = kal.get(Calendar.DAY_OF_MONTH)
        bulan = kal.get(Calendar.MONTH)
        tahun = kal.get(Calendar.YEAR)
    }

    fun fSetTanggal(view: View){
        getTanggal()
        DatePickerDialog(this, this,  tahun, bulan, tanggal).show()
    }

    fun fReservasi(view: View) {
        val toast = Toast.makeText(applicationContext, "Reservasi berhasil", Toast.LENGTH_SHORT)

        toast.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        tanggal = dayOfMonth
        bulan = month
        tahun = year

        teksTanggal = findViewById(R.id.edit_Tanggal)
        teksTanggal.text = "${tanggal} - ${bulan} - ${tahun}"
    }

    /*override fun onBackPressed() {
        super.onBackPressed()
        // Panggil intent untuk memulai ulang aktivitas sebelumnya
        val intent = Intent(this, ReservasiFragment::class.java)
        startActivity(intent)

        // Tutup aktivitas saat ini
        finish()
    }*/
}