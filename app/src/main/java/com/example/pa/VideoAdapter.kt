package com.example.pa

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

data class Video(val id:Int, val title:String, val pix_src:String, val video_src:String, val time:String, val tag:ArrayList<String>)
class VideoAdapter(val videoList: List<Video>,val activity: Activity) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    private var onthumb = false
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val pix: ImageView =view.findViewById(R.id.pix)
        val title: TextView = view.findViewById(R.id.title)
        val time: TextView = view.findViewById(R.id.time)
        val thumb:ImageView = view.findViewById(R.id.thumb)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = videoList[position]
        holder.time.text = item.time
        holder.title.text = item.title
        val uri = Uri.parse(item.pix_src)
        val inputStream = activity.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        holder.pix.setImageBitmap(bitmap)
        //跳转视频落地页
        holder.pix.setOnClickListener {
            val intent = Intent(activity, DisplayActivity::class.java)
            intent.putExtra("id",item.id)
            activity.startActivity(intent)
        }
        //点赞
        holder.thumb.setOnClickListener {
            if(onthumb)
            {
                onthumb=false
                holder.thumb.setImageResource(R.drawable.ic_thumb)
            }else{
                onthumb=true
                holder.thumb.setImageResource(R.drawable.ic_ongood)
            }
        }
    }
    override fun getItemCount() = videoList.size
}