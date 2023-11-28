package com.example.coba_lilmile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID


class MainActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnPindahLogin: Button

    private val db by lazy { FirebaseDatabase.getInstance().reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUserName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSubmit = findViewById(R.id.btnRegistrasi)
        btnPindahLogin = findViewById(R.id.btnPindahLogin)


        btnSubmit.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Setiap Kolom Informasi Wajib Di isi", Toast.LENGTH_SHORT).show()
            } else {
                val pengguna = getDataPengguna(username, email, password)
                setDataToDatabase(pengguna)

                // Logika untuk melakukan register
                // Misalnya, menyimpan email dan password ke database Anda
                val intent = Intent(this, Tambah_DataPertumbuhan ::class.java)
                startActivity(intent)
            }
        }

        btnPindahLogin.setOnClickListener{
            val intent = Intent(this, Login ::class.java)
            startActivity(intent)
        }
    }

    // Mengambil data pengguna dari isian
    private fun getDataPengguna(username: String, email: String, password: String): Pengguna {
        return Pengguna(
            id = UUID.randomUUID().toString(),
            username = username,
            email = email,
            password = password
        )
    }

    // Write data ke database
    private fun setDataToDatabase(pengguna: Pengguna) {
        if (pengguna.username.isNotEmpty() && pengguna.email.isNotEmpty() && pengguna.password.isNotEmpty()) {
            btnSubmit.isEnabled = false
            db.child("user").child(pengguna.id).setValue(pengguna)
                .addOnSuccessListener {
                    etUsername.setText("")
                    etEmail.setText("")
                    etPassword.setText("")
                    btnSubmit.isEnabled = true

                    Toast.makeText(this, "Data berhasil tersimpan", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    btnSubmit.isEnabled = true
                    Toast.makeText(this, "Data gagal tersimpan", Toast.LENGTH_SHORT).show()
                }
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Menyembunyikan Keyboard
        imm.hideSoftInputFromWindow(etUsername.windowToken, 0)
        imm.hideSoftInputFromWindow(etEmail.windowToken, 0)
        imm.hideSoftInputFromWindow(etPassword.windowToken, 0)
    }
}

data class Pengguna(val id: String = UUID.randomUUID().toString(), val username: String, val email: String, val password: String)
