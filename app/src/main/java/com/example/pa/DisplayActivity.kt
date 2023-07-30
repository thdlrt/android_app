package com.example.pa

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.VideoView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

class DisplayActivity : AppCompatActivity() {
    private var videoList = ArrayList<Video>()
    private lateinit var dbHelper: DatabaseHelper
    private var id:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper.getInstance(this)
        id = intent.getIntExtra("id",-1)
        setContentView(R.layout.activity_display)
        //装载视频
        videoList = dbHelper.getAllVideos()
        val viewpager:ViewPager2 = findViewById(R.id.viewPager)
        viewpager.orientation = ViewPager2.ORIENTATION_VERTICAL
        val adapter = VideoPagerAdapter(videoList,this)
        viewpager.adapter = adapter
        val initposition = Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % videoList.size + id - 1
        viewpager.setCurrentItem(initposition, false)
        //自动播放
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val videoView = viewpager.findViewWithTag<VideoView>("videoView$position")
                videoView?.start()
            }
        })
        //设置任务栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLACK
        }
    }
}