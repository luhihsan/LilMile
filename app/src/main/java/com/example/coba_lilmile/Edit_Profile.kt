package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.prefs.Preferences

class Edit_Profile : AppCompatActivity() {

    private lateinit var preference: PreferenceHelper
    private lateinit var databaseReference: DatabaseReference

    private lateinit var et_nama_profile: EditText
    private lateinit var et_username_profile: EditText
    private lateinit var et_email_profile: EditText
    private lateinit var et_nohp_profile: EditText

    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)

        et_nama_profile = findViewById(R.id.editName)
        et_username_profile = findViewById(R.id.editUsername)
        et_email_profile = findViewById(R.id.editEmail)
        et_nohp_profile = findViewById(R.id.editNotelp)

        var idUser: String? = ""

        preference = PreferenceHelper(this)
        databaseReference = FirebaseDatabase.getInstance().reference.child("user")

        if(preference.getValues("email") != ""){
            et_nama_profile.setText(preference.getValues("nama"))
            et_username_profile.setText(preference.getValues("username"))
            et_email_profile.setText(preference.getValues("email"))
            et_nohp_profile.setText(preference.getValues("notelp"))

            idUser = preference.getValues("id").toString()
        }



        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener{
            if(et_nama_profile.text.isNotEmpty() && et_username_profile.text.isNotEmpty() && et_email_profile.text.isNotEmpty() && et_nohp_profile.text.isNotEmpty()){
                val updateData = mapOf<String, String>(
                    "nama" to et_nama_profile.text.toString(),
                    "username" to et_username_profile.text.toString(),
                    "email" to et_email_profile.text.toString(),
                    "notelp" to et_nohp_profile.text.toString()
                )
//
                databaseReference.child("${preference.getValues("id")}").updateChildren(updateData).addOnCanceledListener {
                    Toast.makeText(this,"berhasil update", Toast.LENGTH_LONG).show()
                }
//
            }
        }



    }
}