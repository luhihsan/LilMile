package com.example.coba_lilmile

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class navigation_menu : AppCompatActivity()  {
    fun fHome(view: View) {
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

//    fun fHistory(view: View) {
//        val intent = Intent(this, ::class.java)
//        startActivity(intent)
//    }

    fun fProfile(view: View) {
        val intent = Intent(this, Profile::class.java)
        startActivity(intent)
    }
}