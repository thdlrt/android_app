package com.example.pa

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VideoFragment : Fragment() {
    private var videoList = ArrayList<Video>()
    private lateinit var dbHelper: DatabaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHelper = DatabaseHelper.getInstance(requireActivity())
        val recyclerview:RecyclerView = view.findViewById(R.id.recyclerView)

        //装载视频
        videoList = dbHelper.getAllVideos()
        if(videoList.size==0){
            //初始化数据库
            dbHelper.insertVideo(Video(0,"【最强AI】ChatGPT4.0免费使用教程来了！","android.resource://${requireActivity().packageName}/${R.raw.video_pix_1}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_1}","01:45",arrayListOf("科技","AI")))
            dbHelper.insertVideo(Video(0,"一张图片26.46GB？给你一整个星空！","android.resource://${requireActivity().packageName}/${R.raw.video_pix_2}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_2}","03:11",arrayListOf("摄影","生活")))
            dbHelper.insertVideo(Video(0,"这种电子垃圾放在整个相机全都是很炸裂的","android.resource://${requireActivity().packageName}/${R.raw.video_pix_3}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_3}","00:22",arrayListOf("摄影","搞笑","鬼畜")))
            dbHelper.insertVideo(Video(0,"天上的飞机比你看到的还多!!!","android.resource://${requireActivity().packageName}/${R.raw.video_pix_4}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_4}","00:19",arrayListOf("航空","飞机")))
            dbHelper.insertVideo(Video(0,"家用膨胀螺栓知道该怎么选择了吧","android.resource://${requireActivity().packageName}/${R.raw.video_pix_5}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_5}","00:31",arrayListOf("建筑","装修")))
            dbHelper.insertVideo(Video(0,"咱养狗人也是懂点押韵的","android.resource://${requireActivity().packageName}/${R.raw.video_pix_6}"
                ,"android.resource://${requireActivity().packageName}/${R.raw.video_video_6}","00:23",arrayListOf("宠物","生活")))
            videoList = dbHelper.getAllVideos()
        }
        recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        recyclerview.adapter = VideoAdapter(videoList,requireActivity())
        //搜索功能
        val input: EditText = view.findViewById(R.id.editText2)
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本更改时调用
                val keyword = s.toString()
                videoList = dbHelper.searchVideosByTitle(keyword)
                recyclerview.adapter = VideoAdapter(videoList, requireActivity())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}