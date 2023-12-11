package com.example.coba_lilmile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coba_lilmile.util.DataPengguna
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID


class MainActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnPindahLogin: Button

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private val db by lazy { FirebaseDatabase.getInstance().reference }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUserName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnSubmit = findViewById(R.id.btnRegistrasi)
        btnPindahLogin = findViewById(R.id.btnPindahLogin)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("user")


        btnSubmit.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Setiap Kolom Informasi Wajib Di isi", Toast.LENGTH_SHORT).show()
            } else {
                val pengguna = getDataPengguna(username, email, password)

                loadCheckDB(email, pengguna)
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
            nama = "",
            notelp = "",
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

    private fun loadCheckDB(email: String, pengguna: Pengguna){

        databaseReference.orderByChild("email").equalTo(email).addValueEventListener(object :
            ValueEventListener {
            var regis:Boolean = false
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(!dataSnapshot.exists()){
                    suksesRegis(pengguna)
                    regis = true
                }

                if(dataSnapshot.exists()){
                    if(regis == false){
                        Toast.makeText(
                            this@MainActivity, "Email sudah digunakan!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@MainActivity, databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })

    }

    private fun suksesRegis(pengguna: Pengguna){

        setDataToDatabase(pengguna)
        val intent = Intent(this@MainActivity, Login ::class.java)
        startActivity(intent)
    }
}

data class Pengguna(val id: String = UUID.randomUUID().toString(), val username: String, val email: String, val nama:String, val notelp:String, val password: String)