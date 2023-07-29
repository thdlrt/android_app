package com.example.pa

import android.R
import android.os.Bundle
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import com.example.pa.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        //视图绑定
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //默认视图
        val fragmentManager = supportFragmentManager
        val fragment = HomeFragment()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(com.example.pa.R.id.main_frame, fragment)
        transaction.commit()
        //tab切换点击绑定
        val bottomNavigation: BottomNavigationView =
            findViewById(com.example.pa.R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.pa.R.id.action_home -> {
                    val fragmentManager = supportFragmentManager
                    val fragment = HomeFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(com.example.pa.R.id.main_frame, fragment)
                    transaction.commit()
                    true
                }

                com.example.pa.R.id.action_display -> {
                    val fragmentManager = supportFragmentManager
                    val fragment = VideoFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(com.example.pa.R.id.main_frame, fragment)
                    transaction.commit()
                    true
                }

                com.example.pa.R.id.action_user -> {
                    val fragmentManager = supportFragmentManager
                    val fragment = UserFragment()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(com.example.pa.R.id.main_frame, fragment)
                    transaction.commit()
                    true
                }

                else -> false
            }
        }

    }
}