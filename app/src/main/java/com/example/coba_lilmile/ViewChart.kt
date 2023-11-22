package com.example.coba_lilmile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian

class ViewChart : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_chart)

        // Inisialisasi AnyChartView
        val anyChartView = findViewById<AnyChartView>(R.id.lineChart)

        // Inisialisasi Line Chart
        val lineChart = AnyChart.line()

        // Menambahkan konfigurasi grid ke chart
        lineChart.xGrid(true)
        lineChart.yGrid(true)

        // Menambahkan data ke chart
        val data: List<DataEntry> = listOf(
            ValueDataEntry("3", 4.5),
            ValueDataEntry("8", 7.5),
            ValueDataEntry("12", 8),
            // Tambahkan data sesuai kebutuhan Anda
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
//        anyChartView.setChart(areaRangeChart)
    }





}

