package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Edit_DataPertumbuhan : AppCompatActivity(), OnItemClicked {

    private lateinit var rvReadPertumbuhan: RecyclerView
    private lateinit var adptPertumbuhan: Adapter_DataPertumbuhan
    private val listPertumbuhan = mutableListOf<DataUpdatePertumbuhan>()

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private val db by lazy { RealtimeDatabase.instance()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data_pertumbuhan)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")

        // Initialize RecyclerView and ProgressBar
        rvReadPertumbuhan = findViewById(R.id.rvRead_Pertumbuhan)
        adptPertumbuhan = Adapter_DataPertumbuhan(listPertumbuhan, this)
        rvReadPertumbuhan.layoutManager = LinearLayoutManager(this)
        rvReadPertumbuhan.adapter = adptPertumbuhan

        // Load data from Firebase
        loadDataFromFirebase()
    }

    private fun loadDataFromFirebase() {
        databaseReference.orderByChild("umur_tumbuh").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPertumbuhan.clear()
                if(snapshot.exists()){
                    for (postSnapshot in snapshot.children) {
                        val dataPertumbuhan = postSnapshot.getValue(DataUpdatePertumbuhan::class.java)
                        if (dataPertumbuhan != null) {
                            listPertumbuhan.add(dataPertumbuhan)
                        }
                    }
                    adptPertumbuhan.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DB ERROR", error.message)
                Toast.makeText(
                    this@Edit_DataPertumbuhan,
                    "Error: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun editClicked(data: DataUpdatePertumbuhan) {
        showEditDialog(this, data, db)
    }

    override fun deleteClicked(data: DataUpdatePertumbuhan) {
        // Use a unique identifier (e.g., tgl_tumbuh) as the key for deletion
        val key = data.id
        if (key != null) {
//            databaseReference.child(key).removeValue().addOnSuccessListener {
                db.getReference("pertumbuhan_anak").child(key).removeValue().addOnSuccessListener {
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "ID is null, cannot delete data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Panggil intent untuk memulai ulang aktivitas sebelumnya
        val intent = Intent(this, ViewChart::class.java)
        startActivity(intent)

        // Tutup aktivitas saat ini
        finish()
    }

}
