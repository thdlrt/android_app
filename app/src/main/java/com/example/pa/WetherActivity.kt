package com.example.pa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Window
import android.view.WindowId
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WetherActivity : AppCompatActivity() {
    private var wetherList:ArrayList<Wether> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wether)
        //加载动画
        val loadingView: View = findViewById(R.id.loading_view)
        loadingView.visibility = View.VISIBLE
        //按钮绑定
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //加载
        Handler(Looper.getMainLooper()).postDelayed({
            // 这里做数据加载或初始化视图的工作
            initWethers()
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = WetherAdapter(wetherList)

            // 数据加载完成，隐藏加载动画
            loadingView.visibility = View.GONE
        }, 500)
    }

    private fun initWethers(){
        wetherList.add(Wether("现在",R.drawable.ic_clear_day,"31°"))
        wetherList.add(Wether("11时",R.drawable.ic_clear_day,"32°"))
        wetherList.add(Wether("12时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("13时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("14时",R.drawable.ic_partly_cloud_day,"33°"))
        wetherList.add(Wether("15时",R.drawable.ic_partly_cloud_day,"32°"))
        wetherList.add(Wether("16时",R.drawable.ic_partly_cloud_day,"31°"))
        wetherList.add(Wether("17时",R.drawable.ic_partly_cloud_day,"28°"))
        wetherList.add(Wether("18时",R.drawable.ic_clear_day,"27°"))
        wetherList.add(Wether("19时",R.drawable.ic_clear_day,"25°"))
        wetherList.add(Wether("20时",R.drawable.ic_clear_day,"25°"))
        wetherList.add(Wether("21时",R.drawable.ic_clear_day,"24°"))
        wetherList.add(Wether("22时",R.drawable.ic_clear_day,"24°"))
        wetherList.add(Wether("23时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("24时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("1时",R.drawable.ic_clear_day,"23°"))
        wetherList.add(Wether("2时",R.drawable.ic_clear_day,"22°"))
        wetherList.add(Wether("3时",R.drawable.ic_clear_day,"22°"))
        wetherList.add(Wether("4时",R.drawable.ic_partly_cloud_day,"22°"))
        wetherList.add(Wether("5时",R.drawable.ic_partly_cloud_day,"22°"))
        wetherList.add(Wether("6时",R.drawable.ic_partly_cloud_day,"23°"))
        wetherList.add(Wether("7时",R.drawable.ic_partly_cloud_day,"25°"))
        wetherList.add(Wether("8时",R.drawable.ic_partly_cloud_day,"25°"))
        wetherList.add(Wether("9时",R.drawable.ic_clear_day,"26°"))
    }
}
