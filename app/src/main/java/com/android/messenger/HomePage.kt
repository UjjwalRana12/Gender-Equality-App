package com.android.messenger

import LatestMessageFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    lateinit var bottomNavView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val LatestMessageFragment=LatestMessageFragment()
        val news_fragment=news_fragment()
        val emergency_fragment=emergency_fragment()
        setCurrentFragment(news_fragment)

        bottomNavView=findViewById(R.id.bottomNavigationView)

        bottomNavView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(news_fragment)
                R.id.message->setCurrentFragment(LatestMessageFragment)
                R.id.emergency->setCurrentFragment(emergency_fragment)
            }
            true
        }


    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}