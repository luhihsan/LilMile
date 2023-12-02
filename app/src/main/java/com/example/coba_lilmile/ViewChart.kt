package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
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
import org.w3c.dom.Text


class ViewChart : AppCompatActivity(){


    val db by lazy { FirebaseDatabase.getInstance() }


    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var btnMenu : FloatingActionButton
    private lateinit var btnAdd : FloatingActionButton
    private lateinit var btnHistory : FloatingActionButton

    private lateinit var textHasil: TextView
    private lateinit var textHasilInterp: TextView

    private lateinit var titleStatistic: TextView

    private var clicked = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_chart)

        val bundle = intent.extras
        val viewTinggiBadan = bundle!!.getBoolean("viewTinggiBadan")
        val viewBeratBadan = bundle!!.getBoolean("viewBeratBadan")
        val viewLingkarKepala = bundle!!.getBoolean("viewLingkarKepala")


        btnMenu = findViewById(R.id.btnMenu)
        btnAdd = findViewById(R.id.btnTambahData)
        btnHistory = findViewById(R.id.btnHistory)

        textHasil = findViewById(R.id.textHasil)
        textHasilInterp = findViewById(R.id.textHasilInterp)

        btnMenu.setOnClickListener{
            onAddButtonClicked()
        }
        btnAdd.setOnClickListener{
            tambahData()
        }
        btnHistory.setOnClickListener{
            historyData()
        }


        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")


        titleStatistic = findViewById(R.id.titleStat)

        // Inisialisasi AnyChartView
        val anyChartView = findViewById<AnyChartView>(R.id.lineChart)

        // Inisialisasi Line Chart
        val lineChart = AnyChart.line()

        lineChart.padding(10.0, 20.0, 5.0, 20.0);
        lineChart.yAxis(0).title("Tinggi (cm)");
        lineChart.xAxis(0).title("Usia (bulan)");

        // Menambahkan konfigurasi grid ke chart
        lineChart.xGrid(true)
        lineChart.yGrid(true)

        var benchmarkData: List<DataEntry> = ArrayList()
        var seriesData: List<DataEntry> = ArrayList()
        var indikator: String = ""

        var tinggi: IntArray

        var jumlah: Long = 0


        if(viewBeratBadan == true){
            titleStatistic.setText("Berat Badan")
            benchmarkData += CustomDataEntry("0", 4.4, 2.5)
            benchmarkData += CustomDataEntry("1", 5.8, 3.4)
            benchmarkData += CustomDataEntry("2", 7.1, 4.3)
            benchmarkData += CustomDataEntry("3", 8.0, 5.0)
            benchmarkData += CustomDataEntry("4", 8.7, 5.6)
            benchmarkData += CustomDataEntry("5", 9.3, 6.0)
            benchmarkData += CustomDataEntry("6", 9.8, 6.4)
            benchmarkData += CustomDataEntry("7", 10.3, 6.7)
            benchmarkData += CustomDataEntry("8", 10.7, 6.9)
            benchmarkData += CustomDataEntry("9", 11.0, 7.1)
            benchmarkData += CustomDataEntry("10", 11.4, 7.4)
            benchmarkData += CustomDataEntry("11", 11.7, 7.6)
            benchmarkData += CustomDataEntry("12", 12.0, 7.7)
            benchmarkData += CustomDataEntry("13", 12.3, 7.9)
            benchmarkData += CustomDataEntry("14", 12.6, 8.1)
            benchmarkData += CustomDataEntry("15", 12.8, 8.3)
            benchmarkData += CustomDataEntry("16", 13.1, 8.4)
            benchmarkData += CustomDataEntry("17", 13.4, 8.6)
            benchmarkData += CustomDataEntry("18", 13.7, 8.8)
            benchmarkData += CustomDataEntry("19", 13.9, 8.9)
            benchmarkData += CustomDataEntry("20", 14.2, 9.1)
            benchmarkData += CustomDataEntry("21", 14.5, 9.2)
            benchmarkData += CustomDataEntry("22", 14.7, 9.4)
            benchmarkData += CustomDataEntry("23", 15.0, 9.5)
            benchmarkData += CustomDataEntry("24", 15.5, 9.7)




        }
        else if(viewLingkarKepala == true){
            titleStatistic.setText("Lingkar Kepala")

        }
        else{
            titleStatistic.setText("Tinggi Badan")
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



        }




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
                        val jumlahData = dataSnapshot.childrenCount
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

                            if(viewBeratBadan == true){
                                indikator = "Berat"
                                seriesData += ValueDataEntry(dataAnak.umur_tumbuh, dataAnak.berat_tumbuh)
                            }
                            else if(viewTinggiBadan == true){
                                indikator = "Tinggi"
                                seriesData += ValueDataEntry(dataAnak.umur_tumbuh, dataAnak.tinggi_tumbuh)
                            }



                            /*val dataUmur = dataAnak.umur_tumbuh
                            val dataTinggi = dataAnak.tinggi_tumbuh
                            seriesData += ValueDataEntry(dataUmur, dataTinggi)*/


                        }


                    }
                }




                val series3: Line = areaChart.line(seriesData)
                if(viewBeratBadan == true){
                    series3.name("Berat Anak")
                }
                else if(viewTinggiBadan == true){
                    series3.name("Tinggi Anak")
                }

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

                var sumData: Double = 0.0
                var realData: Any
                var itr2 = 0

                for(ValueDataEntry in seriesData){
                    realData = seriesData[itr2].getValue("value").toString()
                    sumData += realData.toDouble()
                    itr2++
                }

                var sumBenchmark1: Double = 0.0
                var sumBenchmark2: Double = 0.0
                var dataBenchmark1: Any
                var dataBenchmark2:Any
                var itr = 0

                for(dataEntry in benchmarkData){
                    dataBenchmark1 = benchmarkData[itr].getValue("value").toString()
                    sumBenchmark1 += dataBenchmark1.toDouble()

                    dataBenchmark2 = benchmarkData[itr].getValue("value2").toString()
                    sumBenchmark2 += dataBenchmark2.toDouble()
                    itr++

                    if(itr == itr2){
                        break
                    }
                }

                if(sumData<=sumBenchmark1 && sumData>=sumBenchmark2){
                    textHasilInterp.setText("Normal")
                    textHasil.setText("$indikator anak normal. Pantau secara berkala, Ideal: $sumBenchmark1, Tidak Ideal: $sumBenchmark2," +
                            "Data: $sumData")
                }
                else{
                    textHasilInterp.setText("Tidak Normal")
                    textHasil.setText("$indikator anak tidak normal, $sumBenchmark1, $sumBenchmark2, $sumData")
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@ViewChart, databaseError.message,
                    Toast.LENGTH_LONG
                ).show()
            }



        })










    }

    private fun tambahData() {
        var tambahDt = Intent(this@ViewChart, Tambah_DataPertumbuhan::class.java)
        startActivity(tambahDt)
    }

    private fun historyData(){
        var historyDt = Intent(this@ViewChart, Edit_DataPertumbuhan::class.java)
        startActivity(historyDt)
    }

    private fun onAddButtonClicked() {
        setVisibilty(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }
    private fun setVisibilty(clicked: Boolean) {
        if(!clicked){
            btnAdd.visibility = View.VISIBLE
            btnHistory.visibility = View.VISIBLE
        }
        else{
            btnAdd.visibility = View.INVISIBLE
            btnHistory.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked:Boolean) {
        if(!clicked){
            btnAdd.startAnimation(fromBottom)
            btnHistory.startAnimation(fromBottom)
            btnMenu.startAnimation(rotateOpen)
        }
        else{
            btnAdd.startAnimation(toBottom)
            btnHistory.startAnimation(toBottom)
            btnMenu.startAnimation(rotateClose)
        }
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

