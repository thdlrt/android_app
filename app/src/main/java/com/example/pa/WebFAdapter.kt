package com.example.pa

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

data class Webpage(val url:String,val title:String)
class WebFAdapter(val webList: List<Webpage>,val activity:Activity) : RecyclerView.Adapter<WebFAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val url: TextView = view.findViewById(R.id.url)
        val main: ConstraintLayout = view.findViewById(R.id.main)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.web_f_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = webList[position]
        holder.title.text = item.title
        holder.url.text = item.url
        holder.main.setOnClickListener{
            val intent = Intent(activity, WebActivity::class.java)
            val dbHelper = DatabaseHelper.getInstance(activity)
            val id = dbHelper.insertWeb(item.url,"",0,"")
            intent.putExtra("id", id)
            activity.startActivity(intent)
        }
    }
    override fun getItemCount() = webList.size
}