package com.example.pa

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.pa.databinding.ActivityAddBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AddActivity : AppCompatActivity() {
    //新闻结构
    private var title:String = ""
    private var content:String = ""
    private var pix_1:String? = null
    private var pix_2:String? = null
    private val add_btn_1_code:Int = 1
    private val add_btn_2_code:Int = 2
    private lateinit var binding: ActivityAddBinding
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper.getInstance(this)
        //视图绑定
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //按钮绑定
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //提交
        val commit: Button = findViewById(R.id.commit)
        commit.setOnClickListener {
            //数据存储
            title = binding.editTextTitle.text.toString()
            content = binding.editTextTitle3.text.toString()
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "标题或正文不得为空", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.insertNews(News(0,pix_1,pix_2,title,content,R.drawable.ic_hot,"热门","百家号"))
                //返回主界面
                Toast.makeText(this, "发布成功！",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        //获取本地存储（照片）
        binding.addBtn1.setOnClickListener {
            open(add_btn_1_code)
        }

        binding.addBtn2.setOnClickListener {
            open(add_btn_2_code)
        }
        //删除按钮
        val del_1:ImageView = findViewById(R.id.del_1)
        del_1.setOnClickListener {
            pix_1 = null
            del_1.setVisibility(View.INVISIBLE)
            binding.addBtn1.setImageResource(R.drawable.ic_update)
        }
        val del_2:ImageView = findViewById(R.id.del_2)
        del_2.setOnClickListener {
            pix_2 = null
            del_2.setVisibility(View.INVISIBLE)
            binding.addBtn2.setImageResource(R.drawable.ic_update)
        }
    }
    //  打开相册
    fun open(code:Int){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 指定只显⽰图⽚
        intent.type = "image/*"
        startActivityForResult(intent, code)
    }
    //处理选择的照片
    fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }
    //缓存图片
    suspend fun saveBitmapToCache(context: Context, bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val fileName = "my_image_" + System.currentTimeMillis() + ".png"
        val file = File(context.cacheDir, fileName)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
        file.absolutePath
    }

    override fun onActivityResult(requestCode: Int, resultCode:Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            add_btn_1_code -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)
                        binding.addBtn1?.setImageBitmap(bitmap)
                        if(bitmap!=null) {
                            // 使用协程在后台保存位图到缓存
                            GlobalScope.launch {
                                pix_1 = saveBitmapToCache(this@AddActivity, bitmap)
                                withContext(Dispatchers.Main) {
                                    val del_1: ImageView = findViewById(R.id.del_1)
                                    del_1.setVisibility(View.VISIBLE)
                                }
                            }
                        }
                    }
                }
            }
            add_btn_2_code -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)
                        binding.addBtn2?.setImageBitmap(bitmap)
                        if(bitmap!=null) {
                            // 使用协程在后台保存位图到缓存
                            GlobalScope.launch {
                                pix_2 = saveBitmapToCache(this@AddActivity, bitmap)
                                withContext(Dispatchers.Main) {
                                    val del_2: ImageView = findViewById(R.id.del_2)
                                    del_2.setVisibility(View.VISIBLE)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}