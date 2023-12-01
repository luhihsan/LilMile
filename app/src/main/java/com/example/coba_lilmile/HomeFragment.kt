package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Line
import com.anychart.enums.MarkerType
import com.example.coba_lilmile.util.PreferenceHelper
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
    private lateinit var databaseReference: DatabaseReference

    private lateinit var dataTB: TextView
    private lateinit var dataBB: TextView
    private lateinit var dataLK: TextView

    private lateinit var dataTanggal: TextView
    private lateinit var dataUsia: TextView

    private lateinit var usiaAnak: TextView

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

        preference = PreferenceHelper(requireActivity().applicationContext)

        var hitung: Int = 0

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("pertumbuhan_anak")

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


        databaseReference.orderByChild("umur_tumbuh").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    for (userSnapshot in dataSnapshot.children){
                        val jumlahData: Long = dataSnapshot.childrenCount
                        val dataAnak = userSnapshot.getValue(isiDataAnak::class.java)
                        hitung += 1
                        if (dataAnak == null) {

                        } else {

                            if(dataAnak.umur_tumbuh == jumlahData.toString()){
                                dataTB.setText(dataAnak.tinggi_tumbuh.toString())
                                dataBB.setText(dataAnak.berat_tumbuh.toString())
                                var umur = dataAnak.umur_tumbuh
                                dataUsia.setText("0 Tahun $umur Bulan")
                                usiaAnak.setText("0 Tahun $umur Bulan")
                                dataTanggal.setText(dataAnak.tgl_tumbuh)
                                dataLK.setText("Kosong")
                            }

                        }
                    }
                }





            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

}