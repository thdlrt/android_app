package com.example.pa

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream

class WebActivity : AppCompatActivity() {
    private var onstar:Boolean = false
    lateinit private var webview:WebView
    private lateinit var dbHelper: DatabaseHelper
    private var webId:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        dbHelper = DatabaseHelper.getInstance(this)
        //输入跳转
        val input:EditText = findViewById(R.id.editText)
        input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = input.text.toString()
                getWeb(text)
                true
            } else {
                false
            }
        }
        //点击后自动全选文字
        input.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.postDelayed({
                    (v as EditText).selectAll()
                }, 100)
            }
        }
        //按钮绑定
        val back:ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        //菜单栏
        webview = findViewById(R.id.main)
        val bottomNavigation: BottomNavigationView =
            findViewById(com.example.pa.R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.pa.R.id.action_home -> {
                    getWeb("https://www.baidu.com")
                }

                com.example.pa.R.id.action_left -> {
                    webview.goBack()
                }

                com.example.pa.R.id.action_right -> {
                    webview.goForward()
                }

                com.example.pa.R.id.action_multi -> {
                    val intent = Intent(this, MultiActivity::class.java)
                    intent.putExtra("id", webId)
                    startActivityForResult(intent, 1)

                }

                else -> false
            }
            true
        }
        //收藏
        val star:ImageView = findViewById(R.id.star)
        webview.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Handler(Looper.getMainLooper()).postDelayed({
                    refresh2()
                }, 500)
            }
            false
        }
        star.setOnClickListener {
            if(onstar){
                onstar = false
                star.setImageResource(R.drawable.ic_star)
                dbHelper.deleteWebpageByUrl(webview.url!!)
            }else{
                onstar = true
                star.setImageResource(R.drawable.ic_onstar)
                dbHelper.insertWebpage(Webpage(webview.url!!,webview.title!!))
            }
        }
        //关闭虚拟键盘
        val editText: EditText = findViewById(R.id.editText)
        editText.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        //加载页面
        refresh(intent.getIntExtra("id", -1))

    }
    //url刷新
    fun refresh2(){
        val star:ImageView = findViewById(R.id.star)
        val edittext: EditText = findViewById(R.id.editText)
        val newUrl = webview?.url.toString()
        edittext.setText(newUrl)
        if (dbHelper.isWebpageUrlExists(newUrl)) {
            star.setImageResource(R.drawable.ic_onstar)
            onstar = true
        } else {
            star.setImageResource(R.drawable.ic_star)
            onstar = false
        }
    }
    fun refresh(id:Int){
        webId = id
        if(webId==-1){
            webId = dbHelper.insertWeb("","",0,"")
        }
        val web = dbHelper.getWebById(webId!!)
        web?.let{
            //删除旧图片
            if(web.thumbnail!="") {
                val file = File(web.thumbnail)
                file.delete()
            }
            getWeb(it.url)
            webview.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    val scrollPosition = // 从数据库中读取的滚动位置
                        webview.scrollTo(0, it.scrollPosition)
                }
            }

        }
    }
    override fun onPause() {
        super.onPause()
        //删除旧的图片
        val web = dbHelper.getWebById(webId!!)
        web?.let {
            //删除旧图片
            if (web.thumbnail != "") {
                val file = File(web.thumbnail)
                file.delete()
            }
        }
        //对当前页面进行缓存
        val url = webview.url
        val title = webview.title
        val scrollPosition = webview.scrollY
        val thumbnail = captureVisibleThumbnail(webview)
        if(url!=null&&title!=null) {
            dbHelper.updateWeb(webId!!, url, title, scrollPosition, thumbnail)
        }
    }
    //捕获所缩略图
    fun captureVisibleThumbnail(webView: WebView): String {
        val width = webView.width
        val height = webView.height
        if (width > 0 && height > 0) {
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            webView.draw(canvas)
            val timestamp = System.currentTimeMillis()
            val thumbnailFile = File(getExternalFilesDir(null), "thumbnail_$timestamp.png")
            val outputStream = FileOutputStream(thumbnailFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            return thumbnailFile.absolutePath
        } else {
            return ""
        }
    }
    //加载页面
    fun getWeb(title:String){
        var url:String
        if(title==""){
            url="https://www.baidu.com"
        }else if(isUrl(title)){
            url = title
        }else{
            url = "https://www.baidu.com/s?wd=${title}"
            //存储搜索记录
            val dbHelper = DatabaseHelper.getInstance(this)
            dbHelper.insertHistory(title)
        }
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
        webview.loadUrl(url)
        //设置收藏状态
        refresh2()
    }
    //判断是否是网址
    fun isUrl(input: String): Boolean {
        val pattern = Patterns.WEB_URL
        val matcher = pattern.matcher(input)
        return matcher.matches()
    }
    //重写左划
    override fun onBackPressed() {
        if(webview.canGoBack())
            webview.goBack()
        else
            super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        Handler(Looper.getMainLooper()).postDelayed({
            refresh2()
        }, 500)
    }
    //返回值接收
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val id = data?.getIntExtra("id",-1)
                if(id!=webId&&id!=null&&id!=-1)
                    refresh(id)
            }
        }
    }
}