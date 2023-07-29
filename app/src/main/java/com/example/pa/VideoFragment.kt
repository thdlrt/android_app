package com.example.pa

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VideoFragment : Fragment() {
    private var videoList = ArrayList<Video>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview:RecyclerView = view.findViewById(R.id.recyclerView)
        //悬浮按钮
        val btnFloat: FloatingActionButton = view.findViewById(R.id.fab)
        var drawable: Drawable? = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_float_add)
        drawable = DrawableCompat.wrap(drawable!!).mutate()
        val bitmap = (drawable as BitmapDrawable).bitmap
        val scaledDrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 48, 48, true))
        btnFloat.setImageDrawable(scaledDrawable)
        btnFloat.setOnClickListener {

        }
        //装载视频
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        recyclerview.adapter = VideoAdapter(videoList)
    }
}