package com.example.coba_lilmile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Edit_DataPertumbuhan : AppCompatActivity(), OnItemClicked {

    private lateinit var rvReadPertumbuhan: RecyclerView
    private lateinit var adptPertumbuhan: Adapter_DataPertumbuhan
    private val listPertumbuhan = mutableListOf<DataUpdatePertumbuhan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data_pertumbuhan)

        // Initialize RecyclerView and ProgressBar
        rvReadPertumbuhan = findViewById(R.id.rvRead_Pertumbuhan)
        adptPertumbuhan = Adapter_DataPertumbuhan(listPertumbuhan, this)
        rvReadPertumbuhan.layoutManager = LinearLayoutManager(this)
        rvReadPertumbuhan.adapter = adptPertumbuhan

        // TODO: Set up other views and functionality if needed

        // Load data from Firebase
        loadDataFromFirebase()
    }

    // Read data from Firebase
    private fun loadDataFromFirebase() {


        val databaseReference = RealtimeDatabase.instance().getReference("pertumbuhan_anak")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                listPertumbuhan.clear()

                for (postSnapshot in snapshot.children) {
                    val dataPertumbuhan = postSnapshot.getValue(DataUpdatePertumbuhan::class.java)
                    if (dataPertumbuhan != null) {
                        listPertumbuhan.add(dataPertumbuhan)
                    }
                }

                adptPertumbuhan.notifyDataSetChanged()
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
        // Implement edit action if needed
    }

    override fun deleteClicked(data: DataUpdatePertumbuhan) {
        // Implement delete action if needed
    }
}
