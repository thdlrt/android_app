package com.example.pa

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pa.databinding.ActivityEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val back:ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        editor = getSharedPreferences("user",Context.MODE_PRIVATE).edit()
        //修改响应
        binding.constraintLayoutAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        binding.constraintLayoutName.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialog_custom, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setTitle("请输入内容")
            builder.setView(dialogLayout)
            builder.setPositiveButton("确定") { dialogInterface, i ->
                val inputText = editText.text.toString()
                if(inputText=="")
                    Toast.makeText(this, "用户名不能为空",Toast.LENGTH_SHORT).show()
                else {
                    editor.putString("name", inputText)
                    editor.apply()
                }
                refresh()
            }
            builder.setNegativeButton("取消", null)
            builder.show()
        }
        binding.constraintLayoutSex.setOnClickListener {
            val genderOptions = arrayOf("男", "女")
            var selectedGender = 0 // 默认选择的性别
            val builder = AlertDialog.Builder(this)
            builder.setTitle("选择性别")
            builder.setSingleChoiceItems(genderOptions, selectedGender) { dialog, which ->
                selectedGender = which
            }
            builder.setPositiveButton("确定") { dialog, _ ->
                val selectedGenderString = genderOptions[selectedGender]
                editor.putString("sex",selectedGenderString)
                editor.apply()
                refresh()
            }
            builder.setNegativeButton("取消", null)
            val dialog = builder.create()
            dialog.show()
        }
        binding.constraintLayoutDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // 用户选择的日期在这里
                    editor.putString("date","$selectedYear-${selectedMonth + 1}-$selectedDay")
                    editor.apply()
                    refresh()
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        binding.constraintLayoutSig.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.dialog_custom, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setTitle("请输入内容")
            builder.setView(dialogLayout)
            builder.setPositiveButton("确定") { dialogInterface, i ->
                val inputText = editText.text.toString()
                if(inputText=="")
                    Toast.makeText(this, "个性签名不能为空",Toast.LENGTH_SHORT).show()
                else {
                    editor.putString("sig", inputText)
                    editor.apply()
                }
                refresh()
            }
            builder.setNegativeButton("取消", null)
            builder.show()
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }
    fun refresh(){
        //数据显示
        val prefs = getSharedPreferences("user", Context.MODE_PRIVATE)
        val bitmap = loadBitmap(this,prefs.getString("avatar","")!!)
        binding.avatar.setImageBitmap(bitmap)
        binding.name.text = prefs.getString("name","")!!
        binding.sex.text = prefs.getString("sex","")!!
        binding.date.text = prefs.getString("date","")!!
        binding.sig.text = prefs.getString("sig","")!!
    }
    override fun onActivityResult(requestCode: Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)
                        if (bitmap != null) {
                            // 使用协程在后台保存位图到缓存
                            GlobalScope.launch {
                                val pix = saveBitmapToCache(this@EditActivity, bitmap)
                                editor.putString("avatar", pix)
                                editor.apply()
                                withContext(Dispatchers.Main) {
                                   binding.avatar.setImageBitmap(bitmap)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }
    suspend fun saveBitmapToCache(context: Context, bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val fileName = "my_image_" + System.currentTimeMillis() + ".png"
        val file = File(context.cacheDir, fileName)
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.close()
        file.absolutePath
    }
}