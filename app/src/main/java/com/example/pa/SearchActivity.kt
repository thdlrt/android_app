package com.example.pa

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SearchActivity : AppCompatActivity() {
    private var onSee:Boolean = true
    private var historyList = ArrayList<Search>()
    private var recommendList = ArrayList<Search>()
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        dbHelper = DatabaseHelper.getInstance(this)

        //按钮绑定
        val back:ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        //删除历史记录
        val delete:ImageView = findViewById(R.id.imageView2)
        delete.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        //推荐隐藏
        val recommend:ImageView = findViewById(R.id.imageView3)
        val recommend_list:RecyclerView = findViewById(R.id.recyclerView_recommend)
        recommend.setOnClickListener {
            if(onSee){
                onSee = false
                recommend.setImageResource(R.drawable.ic_close)
                recommend_list.setVisibility(View.GONE)
            }else{
                onSee = true
                recommend.setImageResource(R.drawable.ic_open)
                recommend_list.setVisibility(View.VISIBLE)
            }
        }
        initrecommend()
        //推荐列表
        recommend_list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recommend_list.adapter = SearchAdapter(recommendList, this)
        //搜索跳转
        val input:EditText = findViewById(R.id.editText)
        input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val intent = Intent(this, WebActivity::class.java)
                val id = dbHelper.insertWeb(input.text.toString(),"",0,"")
                intent.putExtra("id", id)
                startActivity(intent)
                true
            } else {
                false
            }
        }
    }
    override fun onResume() {

        super.onResume()
        val editText: EditText = findViewById(R.id.editText)
        editText.postDelayed({
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
        //刷新历史记录表
        inithistory()
        val history_list:RecyclerView = findViewById(R.id.recyclerView_history)
        history_list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        history_list.adapter = SearchAdapter(historyList, this)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun inithistory(){
          val data: List<HistoryAdapter.History> = dbHelper.getAllHistory()
            historyList = ArrayList(data.map { history -> Search(history.title) })
    }

    fun initrecommend(){
        recommendList.add(Search("全国两会闭幕"))
        recommendList.add(Search("谷歌新品发布会"))
        recommendList.add(Search("习近平将访俄",R.drawable.ic_hot))
        recommendList.add(Search("小米发布新款手机"))
    }

}
