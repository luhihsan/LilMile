package com.example.coba_lilmile


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.prefs.Preferences

class welcome_page : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnPindahRegis: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        btnLogin = findViewById(R.id.btnLogin)
        btnPindahRegis = findViewById(R.id.btnPindahRegis)

        btnLogin.setOnClickListener {
            // Navigate to the login activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnPindahRegis.setOnClickListener {
            // Navigate to the registration activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}