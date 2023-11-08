package com.example.coba_lilmile

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var etUsername : EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUserName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSubmit = findViewById(R.id.btnSubmit)


        btnSubmit.setOnClickListener {
            val username = etUsername.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Setiap Kolom Informasi Wajib Di isi", Toast.LENGTH_SHORT).show()
            } else {
                // Logika untuk melakukan register
                // Misalnya, menyimpan email dan password ke database Anda
                val intent = Intent(this, Login::class.java)
                startActivity(intent)

            }
        }

    }
}

