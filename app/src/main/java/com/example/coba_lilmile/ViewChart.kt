package com.example.coba_lilmile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Area
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Set
import com.anychart.enums.MarkerType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ViewChart : AppCompatActivity(){


    val db by lazy { FirebaseDatabase.getInstance() }



    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var buttonTambah : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_chart)


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")

        buttonTambah = findViewById(R.id.btnTambahData)

        // Inisialisasi AnyChartView
        val anyChartView = findViewById<AnyChartView>(R.id.lineChart)

        // Inisialisasi Line Chart
        val lineChart = AnyChart.line()

        /*lineChart.padding(10.0, 20.0, 5.0, 20.0);*/
        lineChart.yAxis(0).title("Tinggi (cm)");
        lineChart.xAxis(0).title("Usia (bulan)");

        // Menambahkan konfigurasi grid ke chart
        lineChart.xGrid(true)
        lineChart.yGrid(true)

        var benchmarkData: List<DataEntry> = ArrayList()
        var seriesData: List<DataEntry> = listOf()

        benchmarkData += CustomDataEntry("0", 53.69, 46.09)
        benchmarkData += CustomDataEntry("1", 58.59, 50.79)
        benchmarkData += CustomDataEntry("2", 62.39, 54.39)
        benchmarkData += CustomDataEntry("3", 65.49, 57.29)
        benchmarkData += CustomDataEntry("4", 67.99, 59.69)
        benchmarkData += CustomDataEntry("5", 70.09, 61.69)
        benchmarkData += CustomDataEntry("6", 71.89, 63.29)
        benchmarkData += CustomDataEntry("7", 73.49, 64.79)
        benchmarkData += CustomDataEntry("8", 74.99, 66.19)
        benchmarkData += CustomDataEntry("9", 76.49, 67.49)
        benchmarkData += CustomDataEntry("10", 77.89, 68.69)
        benchmarkData += CustomDataEntry("11", 79.19, 69.89)
        benchmarkData += CustomDataEntry("12", 80.49, 70.99)
        benchmarkData += CustomDataEntry("13", 81.79, 72.09)
        benchmarkData += CustomDataEntry("14", 82.99, 73.09)
        benchmarkData += CustomDataEntry("15", 84.19, 74.09)
        benchmarkData += CustomDataEntry("16", 85.39, 74.99)
        benchmarkData += CustomDataEntry("17", 86.49, 75.99)
        benchmarkData += CustomDataEntry("18", 87.69, 76.89)
        benchmarkData += CustomDataEntry("19", 88.79, 77.69)
        benchmarkData += CustomDataEntry("20", 89.79, 78.59)
        benchmarkData += CustomDataEntry("21", 90.89, 79.39)
        benchmarkData += CustomDataEntry("22", 91.89, 80.19)
        benchmarkData += CustomDataEntry("23", 92.89, 80.99)
        benchmarkData += CustomDataEntry("24", 93.89, 81.69)


        val set = Set.instantiate()
        set.data(benchmarkData)
        val series1TB = set.mapAs("{ x: 'x', value: 'value' }")
        val series2TB = set.mapAs("{ x: 'x', value: 'value2' }")


        val areaChart: Cartesian = AnyChart.area()
        val series1: Area = areaChart.area(series1TB)
        series1.name("Ideal")
        series1.normal().fill("#b8e4a4", 1);
        series1.hovered().fill("#b8e4a4", 1);
        series1.selected().fill("#b8e4a4", 1);
        series1.normal().stroke("3 #dec250")
        series1.selected().stroke("3 #dec250")
        series1.hovered().stroke("3 #dec250")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
            .stroke("1.5 #dec250")
        series1.markers().zIndex(100.0)




        val series2: Area = areaChart.area(series2TB)
        series2.name("Tidak Ideal")
        series2.normal().fill("#ffffff", 1);
        series2.hovered().fill("#ffffff", 1);
        series2.selected().fill("#ffffff", 1);
        series2.stroke("3 #dec250")
        series2.normal().stroke("3 #dec250")
        series2.selected().stroke("3 #dec250")
        series2.hovered().stroke("3 #dec250")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
            .stroke("1.5 #fff")
        series2.markers().zIndex(100.0)



        databaseReference.orderByChild("umur_tumbuh").addValueEventListener(object : ValueEventListener{
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

                            /*val dataUmur = dataAnak.umur_tumbuh
                            val dataTinggi = dataAnak.tinggi_tumbuh
                            seriesData += ValueDataEntry(dataUmur, dataTinggi)*/


                        }
                    }
                }


                val series3: Line = areaChart.line(seriesData)
                series3.name("Tinggi Anak")
                series3.stroke("2 #0066cc")
                series3.hovered().stroke("3 #0066cc")
                series3.hovered().markers().enabled(true)
                series3.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4.0)
                    .stroke("1.5 #0066cc")
                series3.markers().zIndex(100.0)

                // Menetapkan data ke chart
                /*lineChart.data(seriesData)*/

                // Menambahkan chart ke AnyChartView
                /*anyChartView.setChart(lineChart)*/
                anyChartView.setChart(areaChart)

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


    private class CustomDataEntry internal constructor(
        x: String?,
        value: Double?,
        value2: Double?

    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)


        }
    }


}

