package com.example.coba_lilmile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.coba_lilmile.util.PreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class Homepage : AppCompatActivity() {

    private lateinit var pageTB: CardView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var namaUser: TextView
    private lateinit var preference: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        replaceFragment(HomeFragment())

        namaUser = findViewById(R.id.textGreetingUser)
        preference = PreferenceHelper(this)

        namaUser.setText(preference.getValues("username"))

        bottomNavigation = findViewById(R.id.bottom_navigation)

        bottomNavigation.selectedItemId = R.id.menu_1

        findViewById<BottomNavigationView>(R.id.bottom_navigation) .itemActiveIndicatorColor = getColorStateList(R.color.mainBlue)

        bottomNavigation.setOnItemSelectedListener {

            when(it.itemId){

                R.id.menu_1 -> replaceFragment(HomeFragment())
                R.id.menu_2 -> replaceFragment(HistoryFragment())
                R.id.menu_3 -> replaceFragment(ProfileFragment())

                else ->{

                }
            }
            true
        }



        pageTB = findViewById<CardView>(R.id.btnTB)

        pageTB.setOnClickListener{
            val intent = Intent(this, ViewChart::class.java)
            startActivity(intent)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
}