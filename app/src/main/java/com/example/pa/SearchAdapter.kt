package com.example.pa

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

data class Search(val title:String, val type:Int?=null)
class SearchAdapter(val SearchList: List<Search>, val activity: Activity) :RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val type: ImageView =view.findViewById(R.id.type)
        val title: TextView = view.findViewById(R.id.title)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val item = SearchList[position]
        //额外项
        if(position == 9){
            holder.title.setTextColor(Color.parseColor("#000000"))
            holder.title.alpha = 0.4f
            holder.title.text = "查看更多历史"
            holder.type.setImageResource(R.drawable.ic_right)
            holder.type.alpha = 0.4f
            //跳转完整历史记录
            holder.title.setOnClickListener {
                val intent = Intent(activity, HistoryActivity::class.java)
                activity.startActivity(intent)
            }
        }else{
            holder.title.text = item.title
            if(item.type!=null)
                holder.type.setImageResource(item.type)
            //搜索
            holder.title.setOnClickListener {
                val intent = Intent(activity, WebActivity::class.java)
                val dbHelper = DatabaseHelper.getInstance(activity)
                val id = dbHelper.insertWeb(item.title,"",0,"")
                intent.putExtra("id", id)
                activity.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int{
        if(SearchList.size>10)
            return 10
        return SearchList.size
    }
}