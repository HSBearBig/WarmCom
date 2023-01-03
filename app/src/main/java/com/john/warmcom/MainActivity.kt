package com.john.warmcom

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            UserDetails.userUID = auth.currentUser!!.uid
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        setUpTabBar()
    }

    private fun setUpTabBar() {
        val mainPageTabLayout = findViewById<TabLayout>(R.id.MainPageTabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.MainPageViewPager2)
        val adapter = TabPageAdapter(this, mainPageTabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int) {
                mainPageTabLayout.selectTab(mainPageTabLayout.getTabAt(position))
            }
        })

        mainPageTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab)
            {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}