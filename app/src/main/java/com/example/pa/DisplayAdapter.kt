package com.example.pa

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class VideoPagerAdapter(private val videItems: List<Video>, val activity:DisplayActivity) : RecyclerView.Adapter<VideoPagerAdapter.VideoViewHolder>() {
    private var onstar = false
    inner class VideoViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val tag: TextView = view.findViewById(R.id.tag)
        val back:ImageView = view.findViewById(R.id.back)
        val star:ImageView = view.findViewById(R.id.star)
        val share:ImageView = view.findViewById(R.id.share)
        val video:VideoView = view.findViewById(R.id.videoView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.display_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = videItems[position % videItems.size]
        holder.title.text = item.title
        val dbHelper: DatabaseHelper = DatabaseHelper.getInstance(activity)
        val newList = item.tag.map { "#$it" }
        holder.tag.text = newList.joinToString(separator = " ")
        val mediaController = MediaController(activity)
        mediaController.setAnchorView(holder.video)
        holder.video.setMediaController(mediaController)
        holder.video.setVideoURI(Uri.parse(item.video_src))
        holder.video.start()
        val videoView = holder.itemView.findViewById<VideoView>(R.id.videoView)
        videoView.tag = "videoView$position"
        //点击事件
        holder.back.setOnClickListener {
            activity.onBackPressed()
        }
        holder.share.setOnClickListener {
            Toast.makeText(activity, "分享成功!经验+3", Toast.LENGTH_SHORT).show()
        }
        if(dbHelper.isVideoIdExists(item.id)){
            onstar = true
            holder.star.setImageResource(R.drawable.ic_onstar)
        }else{
            onstar = false
            holder.star.setImageResource(R.drawable.ic_star_w)
        }
        holder.star.setOnClickListener {
            if(onstar){
                onstar = false
                holder.star.setImageResource(R.drawable.ic_star_w)
                dbHelper.deleteVideoFavoriteById(item.id)
            }else{
                onstar = true
                holder.star.setImageResource(R.drawable.ic_onstar)
                dbHelper.insertVideoFavorite(VideoFavorite(item.id,item.title))
            }
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}

