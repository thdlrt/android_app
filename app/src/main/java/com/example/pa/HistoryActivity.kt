package com.example.pa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    private var historyList=ArrayList<HistoryAdapter.History>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        //返回
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        //清空历史记录
        val delete: TextView = findViewById(R.id.clean)
        delete.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("注意")
                setMessage("确定清除全部历史记录？")
                setCancelable(true)
                setPositiveButton("确定") { dialog, which ->
                    //删除历史记录
                    val dbHelper = DatabaseHelper.getInstance(context)
                    dbHelper.clearHistory()
                    refresh()
                }
                setNegativeButton("取消") { dialog, which ->
                }
                show()
            }
        }
        //装载表格
        refresh()
    }
    fun refresh(){
        //读取
        inithistory()
        val recyclerView:RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoryAdapter.HistoryAdapter(historyList, this)
    }
    fun inithistory(){
        val dbHelper = DatabaseHelper.getInstance(this)
        historyList = dbHelper.getAllHistory()
    }
}