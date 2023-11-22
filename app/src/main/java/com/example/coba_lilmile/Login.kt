package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    // Dalam kelas MainActivity
    val db by lazy { FirebaseDatabase.getInstance().reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        // Lakukan query ke database untuk memeriksa email
        // (Harap disesuaikan dengan struktur database Anda)
        db.child("user").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var loginSuccess = false

                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(DataPengguna::class.java)

                    // Bandingkan password di database dengan password yang dimasukkan oleh pengguna
                    if (user?.password == password) {
                        // Login berhasil
                        loginSuccess = true
                        Toast.makeText(this@Login, "Login berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, Registrasi_anak::class.java)
                        startActivity(intent)
                        finish() // Optional: Menutup activity login agar tidak dapat dikembalikan lagi
                        break
                    }
                }

                // Jika login gagal, tampilkan toast
                if (!loginSuccess) {
                    Toast.makeText(this@Login, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(this@Login, "Error: " + databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
