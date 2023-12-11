package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Line
import com.anychart.enums.MarkerType
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var pageTB: CardView
    private lateinit var pageBB: CardView
    private lateinit var namaUser: TextView
    private lateinit var preference: PreferenceHelper

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseDatabase2: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var databaseReference2: DatabaseReference

    private lateinit var dataTB: TextView
    private lateinit var dataBB: TextView
    private lateinit var dataLK: TextView

    private lateinit var usiaAnak: TextView
    private lateinit var namaAnak: TextView

    private lateinit var dataTanggal: TextView
    private lateinit var dataUsia: TextView


    private lateinit var btnTambahAnak: FloatingActionButton

    private lateinit var imageCarousel1: ImageView
    private lateinit var imageCarousel2: ImageView
    private lateinit var imageCarousel3: ImageView
    private lateinit var imageCarousel4: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        namaUser = requireView().findViewById(R.id.textGreetingUser)
        pageTB = requireView().findViewById(R.id.btnTB)
        pageBB = requireView().findViewById(R.id.btnBB)

        dataTB = requireView().findViewById(R.id.dataTB)
        dataBB = requireView().findViewById(R.id.dataBB)
        dataLK = requireView().findViewById(R.id.dataLK)
        dataTanggal = requireView().findViewById(R.id.dataTanggal)
        dataUsia = requireView().findViewById(R.id.dataUsia)

        usiaAnak = requireView().findViewById(R.id.usiaAnak)
        namaAnak = requireView().findViewById(R.id.namaAnak)

        btnTambahAnak = requireView().findViewById(R.id.btnTambahAnak)

        preference = PreferenceHelper(requireActivity().applicationContext)

        imageCarousel1 = requireView().findViewById(R.id.imageCarousel1)
        imageCarousel2 = requireView().findViewById(R.id.imageCarousel2)
        imageCarousel3 = requireView().findViewById(R.id.imageCarousel3)
        imageCarousel4 = requireView().findViewById(R.id.imageCarousel4)

        var hitung: Int = 0
        var usiaTemp:String

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase2 = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")
        databaseReference2 = FirebaseDatabase.getInstance().getReference("anak")

        if(preference.getValues("nama")!= ""){
            namaUser.setText(preference.getValues("nama"))
        }
        else{
            namaUser.setText(preference.getValues("username"))
        }



        pageTB.setOnClickListener{
            val goTB = Intent(requireActivity().applicationContext, ViewChart::class.java)
            goTB.putExtra("viewTinggiBadan", true)
            startActivity(goTB)
        }

        pageBB.setOnClickListener {
            val goBB = Intent(requireActivity().applicationContext, ViewChart::class.java)
            goBB.putExtra("viewBeratBadan", true)
            startActivity(goBB)
        }
        btnTambahAnak.setOnClickListener{
            val goTambah = Intent(requireActivity().applicationContext, Registrasi_anak::class.java)
            startActivity(goTambah)
        }
        imageCarousel1.setOnClickListener{
            val goArtikel = Intent(requireActivity().applicationContext, Artikel::class.java)
            startActivity(goArtikel)
        }

        imageCarousel2.setOnClickListener{
            val goArtikel2 = Intent(requireActivity().applicationContext, artikel2::class.java)
            startActivity(goArtikel2)
        }

        imageCarousel3.setOnClickListener{
            val goArtikel3 = Intent(requireActivity().applicationContext, artikel3::class.java)
            startActivity(goArtikel3)
        }

        imageCarousel4.setOnClickListener{
            val goArtikel4 = Intent(requireActivity().applicationContext, artikel4::class.java)
            startActivity(goArtikel4)
        }





        var idTemp = preference.getValues("id").toString()

        /*Toast.makeText(activity, "$idTemp", Toast.LENGTH_SHORT).show()*/
        loadDataAnak(idTemp)

        loadDataTumbuh()


    }

    private fun loadDataTumbuh(){
        databaseReference.orderByChild("umur_tumbuh").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val jumlahData: Long = dataSnapshot.childrenCount
                        var maxUmur: Int = 0
                        val isiDataAnak = userSnapshot.getValue(isiDataAnak::class.java)
                        if (isiDataAnak == null) {

                        } else {
                            if(isiDataAnak.idAkun.equals(preference.getValues("id"))){
                                if(isiDataAnak.umur_tumbuh.equals(jumlahData.toString())){
                                    dataTB.setText(isiDataAnak.tinggi_tumbuh.toString())
                                    dataBB.setText(isiDataAnak.berat_tumbuh.toString())
                                    var umur = isiDataAnak.umur_tumbuh
                                    dataUsia.setText("0 Tahun $umur Bulan")
                                    usiaAnak.setText(dataUsia.text)
                                    dataTanggal.setText(isiDataAnak.tgl_tumbuh)
                                    dataLK.setText("Kosong")
                                    break
                                }
                            }
                            /*else{
                                dataTB.setText(dataAnak.tinggi_tumbuh.toString())
                                dataBB.setText(dataAnak.berat_tumbuh.toString())
                                var umur = dataAnak.umur_tumbuh
                                dataUsia.setText("0 Tahun $umur Bulan")
                                usiaAnak.setText(dataUsia.text)
                                dataTanggal.setText(dataAnak.tgl_tumbuh)
                                dataLK.setText("Kosong")
                            }*/

                        }
                    }
                }





            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    private fun loadDataAnak(idTemp: String) {
        databaseReference2.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataAnakSnapshot: DataSnapshot) {
                if(dataAnakSnapshot.exists()){
                    var jumlah = dataAnakSnapshot.childrenCount
                    for (getdataSnapshot in dataAnakSnapshot.children){
                        var dataDiriAnak = getdataSnapshot.getValue(DataAnak::class.java)
                        if (dataDiriAnak == null) {
                            Toast.makeText(activity, "Data anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                        } else {
                            if(dataDiriAnak.idAkun.equals(idTemp)){
                                btnTambahAnak.visibility = View.INVISIBLE
                                namaAnak.setText(dataDiriAnak.fullName)
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(activity, "Data anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

}