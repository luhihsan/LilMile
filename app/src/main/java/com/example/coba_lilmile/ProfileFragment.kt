package com.example.coba_lilmile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.coba_lilmile.util.PreferenceHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var keProfil: Button
    /*private lateinit var keSetting: Button
    private lateinit var keAlamat: Button*/

    private lateinit var preference: PreferenceHelper

    private lateinit var textNamaProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preference = PreferenceHelper(requireActivity().applicationContext)
        textNamaProfile = requireView().findViewById(R.id.textNamaProfile)
        keProfil = requireView().findViewById(R.id.btnkeProfil)

        if(preference.getValues("nama")!= ""){
            textNamaProfile.setText(preference.getValues("nama"))
        }
        else{
            textNamaProfile.setText(preference.getValues("username"))
        }

        keProfil.setOnClickListener{
            val goEditProfile = Intent(requireActivity().applicationContext, Edit_Profile::class.java)
            startActivity(goEditProfile)
        }
    }

}