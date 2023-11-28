package com.example.coba_lilmile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewChart : AppCompatActivity(){


    val db by lazy { FirebaseDatabase.getInstance() }



    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_chart)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")

        // Inisialisasi AnyChartView
        val anyChartView = findViewById<AnyChartView>(R.id.lineChart)

        // Inisialisasi Line Chart
        val lineChart = AnyChart.line()

        // Menambahkan konfigurasi grid ke chart
        lineChart.xGrid(true)
        lineChart.yGrid(true)
        var seriesData: List<DataEntry> = listOf()

        databaseReference.orderByChild("tgl_tumbuh").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val jumlahData: Long = dataSnapshot.childrenCount
                        val dataAnak = userSnapshot.getValue(isiDataAnak::class.java)
                        if (dataAnak == null) {
                            Toast.makeText(
                                this@ViewChart, "Data tidak ditemukan",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            /*Toast.makeText(
                                this@ViewChart, "Data ditemukan, jumlah: $jumlahData",
                                Toast.LENGTH_LONG
                            ).show()*/

                            seriesData += ValueDataEntry(dataAnak.umur_tumbuh, dataAnak.tinggi_tumbuh)


                        }
                    }
                }


                // Menetapkan data ke chart
                lineChart.data(seriesData)

                // Menambahkan chart ke AnyChartView
                anyChartView.setChart(lineChart)

                Toast.makeText(
                    this@ViewChart, "Data berhasil ditemukan",
                    Toast.LENGTH_LONG
                ).show()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@ViewChart, databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })


    }

   /* private fun getData(): List<DataEntry>? {



    }*/







}

