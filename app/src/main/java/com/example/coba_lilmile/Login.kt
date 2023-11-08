package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvSignIn = findViewById(R.id.tvSignIn)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                // Logika untuk melakukan login
                // Misalnya, memeriksa email dan password dengan database Anda

                val intent = Intent(this, Registrasi_anak::class.java)
                startActivity(intent)
            }
        }

        tvSignIn.setOnClickListener {
            // Arahkan pengguna ke halaman pendaftaran akun (activity_main)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
