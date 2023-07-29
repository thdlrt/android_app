package com.example.pa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MultiActivity : AppCompatActivity() {
    var webId:Int? = null
    private var webList=ArrayList<Web>()
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi)
        webId = intent.getIntExtra("id",-1)
        dbHelper = DatabaseHelper.getInstance(this)

        //按钮绑定
        val back: TextView = findViewById(R.id.back)
        back.setOnClickListener{
            this.onBackPressed()
        }

        //添加
        val add: Button = findViewById(R.id.add)
        add.setOnClickListener{
            webId = dbHelper.insertWeb("","",0,"")
            onBackPressed()
        }

        //清空
        val clean: TextView = findViewById(R.id.clean)
        clean.setOnClickListener {
            dbHelper.clearWeb()
            webId = dbHelper.insertWeb("","",0,"")
            onBackPressed()
        }
        //窗口显示
        refresh()
    }
    //刷新显示
    fun refresh(){
        webList = ArrayList(dbHelper.getAllWebs())
        val recyclerview:RecyclerView = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerview.adapter = WebAdapter(webList, this)
    }
    //返回数据
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("id", webId)
        setResult(RESULT_OK, intent)
        finish()
    }
    //检查是否删除了当前界面
    fun check(id:Int){
        if(id==webId){
            if(webList.size==0){
                webId = dbHelper.insertWeb("","",0,"")
                onBackPressed()
            }
            else{
                webId = webList[0].id
            }
        }
    }
}