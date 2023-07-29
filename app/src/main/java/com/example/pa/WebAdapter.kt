package com.example.pa

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Web(val id:Int, val url:String, val title:String, val scrollPosition:Int, val thumbnail:String)
class WebAdapter(val webList: List<Web>, val activity: MultiActivity) : RecyclerView.Adapter<WebAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val content: ImageView =view.findViewById(R.id.content)
        val title: TextView = view.findViewById(R.id.title)
        val del:ImageView = view.findViewById(R.id.close)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.web_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = webList[position]
        holder.title.text = item.title
        val bitmap = BitmapFactory.decodeFile(item.thumbnail)
        holder.content.setImageBitmap(bitmap)
        //删除窗口
        holder.del.setOnClickListener {
            val id:Int = webList[position].id
            val dbHelper = DatabaseHelper.getInstance(activity)
            dbHelper.deleteWeb(id)
            activity.refresh()
            activity.check(id)
        }
        //选择窗口
        holder.content.setOnClickListener {
            val id:Int = webList[position].id
            activity.webId = id
            activity.onBackPressed()
        }
    }
    override fun getItemCount() = webList.size
}