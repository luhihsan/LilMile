package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.coba_lilmile.util.DataPengguna
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Profile : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var btnLogout: Button

    private lateinit var preference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        preference = PreferenceHelper(this)

        bottomNavigation = findViewById(R.id.bottomNavigationView)

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener{
            pushLogout()
        }

        bottomNavigation.selectedItemId = R.id.menu_3

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).itemActiveIndicatorColor = getColorStateList(R.color.mainBlue)


    }

    fun fEditProfile(view: View) {
        val intent = Intent(this, Edit_Profile::class.java)
        startActivity(intent)
    }

    fun fAlamat(view: View) {
        val intent = Intent(this, Tambah_Alamat::class.java)
        startActivity(intent)
    }

    private fun pushLogout() {
        preference.clear()
        var goHome = Intent(this, welcome_page::class.java)

        startActivity(goHome)

        finishAffinity()
    }

}