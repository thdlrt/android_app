package com.example.pa

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.pa.databinding.ActivityNewsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {
    private var onstar:Boolean = false
    private var like:Boolean = false
    private lateinit var binding:ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //返回按钮
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        //收藏
        val star:ImageView = findViewById(R.id.star)
        star.setOnClickListener {
            if(onstar){
                onstar = false
                star.setImageResource(R.drawable.ic_star)
            }else{
                onstar = true
                star.setImageResource(R.drawable.ic_onstar)
            }
        }
        //按钮配置
        val btn_like: Button = findViewById(R.id.good)
        btn_like.text = "点赞"
        var drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_good)
        drawable = DrawableCompat.wrap(drawable!!).mutate()
        drawable.setBounds(0, 0, 48, 48) // 设置图标的宽度和高度
        btn_like.setCompoundDrawables(drawable, null, null, null)

        val btn_dislike: Button = findViewById(R.id.dislike)
        btn_dislike.text = "不喜欢"
        var drawable2: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_delete)
        drawable2 = DrawableCompat.wrap(drawable2!!).mutate()
        drawable2.setBounds(0, 0, 48, 48) // 设置图标的宽度和高度
        btn_dislike.setCompoundDrawables(drawable2, null, null, null)

        //点赞
        btn_like.setOnClickListener {
            if(like){
                like = false
                var drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_good)
                drawable = DrawableCompat.wrap(drawable!!).mutate()
                drawable!!.setBounds(0, 0, 48, 48) // 设置图标的宽度和高度
                btn_like.setCompoundDrawables(drawable, null, null, null)
            }else{
                like = true
                var drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_ongood)
                drawable = DrawableCompat.wrap(drawable!!).mutate()
                drawable!!.setBounds(0, 0, 48, 48) // 设置图标的宽度和高度
                btn_like.setCompoundDrawables(drawable, null, null, null)
            }
        }
        //不喜欢
        btn_dislike.setOnClickListener {
            Toast.makeText(this, "感谢反馈!",Toast.LENGTH_SHORT).show()
        }
        //转发
        val share:ImageView = findViewById(R.id.share)
        share.setOnClickListener {
            Toast.makeText(this, "分享成功!经验+3",Toast.LENGTH_SHORT).show()
        }
        val id = intent.getIntExtra("id",-1)
        Log.w("assert","$id")
        val dbHelper = DatabaseHelper.getInstance(this)
        var data = dbHelper.getNewsById(id)
        //显示
        data?.let {
            binding.title.text = it.title
            binding.writer.text = it.writer
            if(it.bigPix!=null) {
                binding.pix.visibility = View.VISIBLE
                val myBitmap = BitmapFactory.decodeFile(it.bigPix)
                binding.pix.setImageBitmap(myBitmap)
            }
            binding.content.text = it.content
        }
    }
}