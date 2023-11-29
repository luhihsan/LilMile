package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var preference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        preference = PreferenceHelper(this)

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.menu_3

        findViewById<BottomNavigationView>(R.id.bottom_navigation) .itemActiveIndicatorColor = getColorStateList(R.color.mainBlue)


    }

    fun fEditProfile(view: View) {
        val intent = Intent(this, Edit_Profile::class.java)
        startActivity(intent)
    }

    fun fAlamat(view: View) {
        val intent = Intent(this, Tambah_Alamat::class.java)
        startActivity(intent)
    }
}