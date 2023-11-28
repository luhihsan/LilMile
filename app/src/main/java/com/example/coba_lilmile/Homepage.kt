package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.cardview.widget.CardView

class Homepage : AppCompatActivity() {

    private lateinit var pageTB: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val masukTB = findViewById<CardView>(R.id.btnTB)

        masukTB.setOnClickListener{
            val intent = Intent(this, ViewChart::class.java)
            startActivity(intent)
        }
    }
}