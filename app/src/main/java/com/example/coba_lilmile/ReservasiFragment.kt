package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ReservasiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservasiFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var b_ismangoen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservasi, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        b_ismangoen = requireView().findViewById(R.id.b_ismangoen)

        b_ismangoen.setOnClickListener{
            val intent = Intent(requireActivity().applicationContext, ReservasiActivity::class.java)
            startActivity(intent)
        }


    }

    /*fun fpindah() {
        val intent = Intent(requireActivity().applicationContext, ReservasiActivity::class.java)
        startActivity(intent)
    }*/





}