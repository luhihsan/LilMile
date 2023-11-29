package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homepage : AppCompatActivity() {

    private lateinit var pageTB: CardView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        /*bottomNavigation.selectedItemId = R.id.item_1*/

        pageTB = findViewById<CardView>(R.id.btnTB)

        pageTB.setOnClickListener{
            val intent = Intent(this, ViewChart::class.java)
            startActivity(intent)
        }
    }
}