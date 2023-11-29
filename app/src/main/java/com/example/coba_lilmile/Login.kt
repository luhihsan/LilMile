package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coba_lilmile.util.DataPengguna
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnPindahRegis: Button
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    // Dalam kelas MainActivity
    /*val db by lazy { FirebaseDatabase.getInstance().reference }*/

    lateinit var preference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        preference = PreferenceHelper(this)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnPindahRegis = findViewById(R.id.btnPindahRegis)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user")

        /*preference = Preferences(this)*/

        /*btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }*/

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                pushLoginUser(email, password)
            }
        }

        btnPindahRegis.setOnClickListener{
            val intent = Intent(this, MainActivity ::class.java)
            startActivity(intent)
        }
    }

    /*private fun tesLoginUser(email:String, password:String){
        if(email!=null){
            if(email.equals(emailBener) && password.equals(passwordBener)){
                Toast.makeText(
                    this@Login, "User ditemukan",
                    Toast.LENGTH_LONG
                ).show()
                var goHome = Intent(this@Login, Homepage::class.java)
                startActivity(goHome)
                finishAffinity()
            }
        }
    }*/

    private fun pushLoginUser(email: String, password: String) {
        databaseReference.orderByChild("email").equalTo(email).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val user = userSnapshot.getValue(DataPengguna::class.java)
                        if (user == null) {
                            Toast.makeText(
                                this@Login, "User tidak ditemukan",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else if(user != null ) {
                            if (user.password == password) {
                                Toast.makeText(
                                    this@Login, "User ditemukan",
                                    Toast.LENGTH_LONG
                                ).show()

                                preference.setValues("id", user.id.toString())
                                preference.setValues("username", user.username.toString())
                                preference.setValues("email", user.email.toString())
                                preference.setValues("password", user.password.toString())
                                preference.setValues("status", "1")

                                var goHome = Intent(this@Login, HomeActivity::class.java)
                                startActivity(goHome)
                                finishAffinity()
                            } else {
                                Toast.makeText(
                                    this@Login, "Password salah!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@Login, databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    /*private fun loginUser(email: String, password: String) {
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
                        Log.d("Login", "Login sukses!")
                        loginSuccess = true
                        Toast.makeText(this@Login, "Login berhasil", Toast.LENGTH_SHORT).show()
                       *//* val intent = Intent(this@Login, Registrasi_anak::class.java)
                        startActivity(intent)*//*

                        val intent = Intent(this@Login, Homepage::class.java)
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
    }*/


}
