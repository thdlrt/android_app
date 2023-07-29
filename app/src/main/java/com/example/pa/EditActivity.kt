package com.example.pa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val back:ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}