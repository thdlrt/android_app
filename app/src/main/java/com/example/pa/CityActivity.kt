package com.example.pa

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class CityActivity : AppCompatActivity() {
    var chooseList=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

    }

    override fun onResume() {
        super.onResume()
        val cityList: ArrayList<City> = arrayListOf(
            City("北京"),
            City("天津"),
            City("上海"),
            City("重庆"),
            City("哈尔滨"),
            City("长春"),
            City("沈阳"),
            City("呼和浩特"),
            City("石家庄"),
            City("太原"),
            City("西安"),
            City("济南"),
            City("乌鲁木齐"),
            City("拉萨"),
            City("西宁"),
            City("兰州"),
            City("郑州"),
            City("南京"),
            City("武汉"),
            City("杭州"),
            City("合肥"),
            City("福州"),
            City("南昌"),
            City("长沙"),
            City("贵阳"),
            City("成都"),
            City("昆明"),
            City("南宁"),
            City("海口"),
            City("广州"),
            City("深圳")
        )
        val prefs = getSharedPreferences("wether", Context.MODE_PRIVATE)
        val json = prefs.getString("city", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {}.type
        chooseList = gson.fromJson(json, type)
        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CityAdapter(cityList,this)
    }
}