package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.renderscript.Sampler.Value
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
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

        databaseReference.orderByChild("tgl_tumbuh").equalTo("1").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val dataAnak = userSnapshot.getValue(isiDataAnak::class.java)
                        if (dataAnak == null) {
                            Toast.makeText(
                                this@ViewChart, "Data tidak ditemukan",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@ViewChart, "Data ditemukan",
                                Toast.LENGTH_LONG
                            ).show()
                            val tb1 = ValueDataEntry("3",4.5)
                            val tb2 = ValueDataEntry("8", 7.5)
                            val tb3 = ValueDataEntry("12", 8)
                            val tb4 = ValueDataEntry(dataAnak.umur_tumbuh, dataAnak.tinggi_tumbuh)
                            val tb5 = ValueDataEntry(dataAnak.umur_tumbuh, dataAnak.tinggi_tumbuh)
                            val data: List<DataEntry> = listOf(
                                tb1, tb2, tb3, tb4, tb5
                            )

                            //        // Inisialisasi Area Range Chart
                            //        val areaRangeChart: Cartesian = AnyChart.area()
                            //
                            //
                            //        // Menambahkan data ke chart
                            //        val dataArea1: List<ValueDataEntry> = listOf(
                            //            ValueDataEntry("A", 10, 20),
                            //            ValueDataEntry("B", 15, 25)
                            //        // ... tambahkan data range lainnya
                            //        )
                            //
                            //        areaRangeChart.rangeArea(dataArea1)

                            // Menetapkan data ke chart
                            lineChart.data(data)

                            // Menambahkan chart ke AnyChartView
                            anyChartView.setChart(lineChart)
                        }
                    }
                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@ViewChart, databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        // Menambahkan data ke chart



    }

   /* private fun getData(): List<DataEntry>? {



    }*/







}

