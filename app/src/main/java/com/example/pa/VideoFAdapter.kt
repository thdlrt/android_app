package com.example.pa

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

data class VideoFavorite(val videoId:Int,val title:String)
class VideoFAdapter(val videoList: List<VideoFavorite>,val activity: Activity) : RecyclerView.Adapter<VideoFAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val main: ConstraintLayout = view.findViewById(R.id.main)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_f_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = videoList[position]
        holder.title.text = item.title
        holder.main.setOnClickListener{
            val intent = Intent(activity, DisplayActivity::class.java)
            intent.putExtra("id", item.videoId)
            activity.startActivity(intent)
        }
    }
    override fun getItemCount() = videoList.size
}