package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DaftarDokter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_dokter)
    }

    fun fpindah(view: View) {
        val intent = Intent(this, ReservasiActivity::class.java)
        startActivity(intent)
    }
}