package com.example.pa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowId
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WetherActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var CityList = ArrayList<String>()
    private var city:String = ""
    private var i:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wether)
        //按钮绑定
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val add:ImageView = findViewById(R.id.add)
        add.setOnClickListener {
            val intent = Intent(this, CityActivity::class.java)
            startActivity(intent)
        }
        val del:ImageView = findViewById(R.id.map)
        del.setOnClickListener {
            if(CityList.size==1)
                return@setOnClickListener
            CityList.remove(city)
            val gson = Gson()
            val json = gson.toJson(CityList)
            val prefs = getSharedPreferences("wether", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("city", json)
            editor.apply()
            refresh()
        }
        val viewPager:ViewPager2 = findViewById(R.id.main)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                city = CityList[position]
                i = position
                //更新活动页面对应的点
                val dotsLayout = findViewById<LinearLayout>(R.id.dotsLayout)
                for (i in 0 until dotsLayout.childCount) {
                    val dot = dotsLayout.getChildAt(i)
                    if (i == position) {
                        dot.setBackgroundResource(R.drawable.indicator_active) // 当前页面的点样式
                    } else {
                        dot.setBackgroundResource(R.drawable.indicator_inactive) // 其他页面的点样式
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
    fun refresh(){
        val viewPager:ViewPager2 = findViewById(R.id.main)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val prefs = getSharedPreferences("wether", Context.MODE_PRIVATE)
        if (!prefs.contains("city")) {
            val cityList = arrayListOf("北京")
            val gson = Gson()
            val json = gson.toJson(cityList)
            prefs.edit().putString("city", json).apply()
        }
        val json = prefs.getString("city", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        CityList = gson.fromJson(json, type)
        if(city=="")
            city = CityList[0]
        viewPager.adapter = CWetherAdapter(CityList,this)
        //更新下面点的数目
        updateDots(CityList.size)
    }
    fun updateDots(count: Int) {
        val dotsLayout = findViewById<LinearLayout>(R.id.dotsLayout)
        dotsLayout.removeAllViews() // 先移除所有点

        for (i in 0 until count) {
            val dot = View(this)
            val params = LinearLayout.LayoutParams(36, 36) // 设置点的大小
            params.setMargins(6, 0, 6, 0) // 设置点之间的间距
            dot.layoutParams = params
            dot.setBackgroundResource(R.drawable.indicator_inactive) // 设置默认的点样式
            dotsLayout.addView(dot) // 将点添加到布局中
        }
    }
}
